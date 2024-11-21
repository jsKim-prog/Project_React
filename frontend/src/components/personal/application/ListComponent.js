import { useEffect, useState } from "react";
import useCustomMove from "../../../hooks/useCustomList";
import useChangeData from "../../../hooks/useChangeData";
import { Button, Card, CardBody, CardTitle, Table, Input } from "reactstrap";
import PageComponent from "../../common/PageComponent";
import { list } from "../../../api/applicationAPI";
import AppAddModal from "../Modal/AppAddModal";
import AppOneModal from "../Modal/AppOneModal";

// 기본 설정값
const pageState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

const ListComponent = () => {
  const { page, size, moveToList, refresh } = useCustomMove();
  const [fetching, setFetching] = useState(false);
  const [serverData, setServerData] = useState({ ...pageState });

  // 검색어 상태 관리
  const [searchQuery, setSearchQuery] = useState("");
  const [cursorStyle, setCursorStyle] = useState('default'); // 마우스 커서 상태 관리

  useEffect(() => {
    setFetching(true);
    // 데이터 요청
    list({ page, size, searchQuery })
      .then((data) => {
        console.log("Fetched data: ", data);
        setServerData(data);
        setFetching(false);
      })
      .catch((error) => {
        console.error("Error fetching data: ", error);
        setFetching(false);
      });
  }, [page, size, refresh, searchQuery]); // 검색어가 변경되면 재호출

  // 검색어 입력값 변경 처리
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value); // 검색어 업데이트
  };

  // 검색 버튼 클릭 처리
  const handleSearch = () => {
    // 검색어에 대한 필터링을 반영하도록 state 업데이트
    setFetching(true);
    list({ page, size, searchQuery })
      .then((data) => {
        console.log("Search result: ", data);
        setServerData(data);
        setFetching(false);
      })
      .catch((error) => {
        console.error("Error during search: ", error);
        setFetching(false);
      });
  };

  // 모달 상태 관리
  const [selectedNo, setSelectedNo] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalOpen_register, setIsModalOpen_register] = useState(false);
  const { changeTeamName, changeJoinStatus } = useChangeData();

  const openModal = (no) => {
    if (no) {
      setSelectedNo(no);
      setIsModalOpen(true);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedNo(null);
    refreshData();
  };

  const openModal_register = () => {
    setIsModalOpen_register(true);
  };

  const closeModal_register = () => {
    setIsModalOpen_register(false);
    refreshData();
  };

  // 데이터를 새로 불러오는 함수
  const refreshData = async () => {
    setFetching(true);
    try {
      const data = await list({ page, size, searchQuery });
      console.log("Refreshed data: ", data);
      setServerData(data);
    } catch (error) {
      console.error("Error fetching data while refreshing: ", error);
    } finally {
      setFetching(false);
    }
  };

  return (
    <div>
      <AppOneModal no={selectedNo} isOpen={isModalOpen} closeModal={closeModal} />
      <AppAddModal isOpen={isModalOpen_register} closeModal={closeModal_register} />
      
      <Card>
        <CardBody>
          <CardTitle tag="h5" className="align-middle">사원 명부</CardTitle>
          
          {/* 검색어 입력 필드 및 버튼 */}
          <div className="d-flex mb-3">
            <Input
              type="text"
              name="search"
              placeholder="이름, 지원부서, 지원결과로 검색"
              value={searchQuery}
              onChange={handleSearchChange}
              className="me-2"
            />
            <Button onClick={handleSearch}>
              검색
            </Button>
          </div>

          <Table className="no-wrap mt-3" responsive borderless>
            <thead>
              <tr>
                <td className="align-right" colSpan={6}>
                  <span
                    className="ms-3 badge bg-success rounded-pill"
                    type="button"
                    onClick={openModal_register}
                  >
                    입사지원서입력
                  </span>
                </td>
              </tr>
              <tr>
                <th>입사지원번호</th>
                <th>이름</th>
                <th>이메일</th>
                <th>전화번호</th>
                <th>지원부서</th>
                <th>지원결과</th>
              </tr>
            </thead>
            <tbody>
              {serverData.dtoList.map((member) => (
                <tr className="border-top" key={member.no}>
                  <td>
                    <div className="d-flex align-items-center p-2">
                      <div className="ms-3">
                        <span className="text-muted" onClick={() => openModal(member.no)}>
                          {member.no}
                        </span>
                      </div>
                    </div>
                  </td>
                  <td>{member.name}</td>
                  <td>{member.mail}</td>
                  <td>{member.phoneNum}</td>
                  <td>{changeTeamName(member.organizationTeam)}</td>
                  <td>{changeJoinStatus(member.joinStatus)}</td>
                </tr>
              ))}
              <tr>
                <td colSpan="6">
                  <PageComponent
                  serverData={serverData} 
                  movePage={moveToList} 
                  cursorStyle={cursorStyle} 
                  setCursorStyle={setCursorStyle}
                  searchQuery={searchQuery} // 검색어 전달
                  />
                </td>
              </tr>
            </tbody>
          </Table>
        </CardBody>
      </Card>
    </div>
  );
};

export default ListComponent;
