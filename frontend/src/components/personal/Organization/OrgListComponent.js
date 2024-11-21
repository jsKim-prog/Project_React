import { Button, Card, CardBody, CardTitle, Table, Input } from "reactstrap";
import { useEffect, useState } from "react";
import { list } from "../../../api/organizationAPI";
import OrgOneModal from "../Modal/OrgOneModal";
import useCustomMove from "../../../hooks/useCustomList";
import PageComponent from "../../common/PageComponent";
import useChangeData from "../../../hooks/useChangeData";
import debounce from "lodash.debounce";

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

const OrgListComponent = () => {
  const { page, size, moveToList, refresh } = useCustomMove();
  const [fetching, setFetching] = useState(false);
  const [serverData, setServerData] = useState({ ...pageState });
  const [selectedMno, setSelectedMno] = useState(null); // 선택된 사원 번호 상태 관리
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달 상태 관리
  const { changeTeamName, changeRoleName } = useChangeData();
  const [searchQuery, setSearchQuery] = useState("");
  const [cursorStyle, setCursorStyle] = useState('default'); // 마우스 커서 상태 관리

  // 서버 데이터 fetch
  const fetchData = debounce(() => {
    setFetching(true);
    list({ page, size, searchQuery })
      .then((data) => {
        setServerData(data);
        setFetching(false);
      })
      .catch((error) => {
        console.error("Error fetching data : ", error);
        setFetching(false);
      });
  }, 500); // 500ms 후에 데이터 요청

  useEffect(() => {
    fetchData(); // 검색어가 바뀔 때마다 서버 요청
  }, [searchQuery, page, size, refresh]);

  // 검색어 입력값 변경 처리
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value.trim()); // 검색어 업데이트
  };

  // 검색 버튼 클릭 처리
  const handleSearch = () => {
    setFetching(true);
    // 페이지를 0으로 설정하여 첫 번째 페이지부터 결과를 가져옵니다.
    list({ page: 0, size, searchQuery })
      .then((data) => {
        setServerData(data);
        setFetching(false);
      })
      .catch((error) => {
        console.error("Error during search: ", error);
        setFetching(false);
      });
  };

  // 1인 선택 시의 모달
  const openModal = (mno) => {
    if (mno) {
      setSelectedMno(mno); // 선택된 mno 상태 저장
      setIsModalOpen(true); // 모달 열기
    }
  };

  const closeModal = () => {
    setIsModalOpen(false); // 모달 닫기
    setSelectedMno(null); // 선택된 mno 초기화
  };

  return (
    <div>
      <OrgOneModal mno={selectedMno} isOpen={isModalOpen} closeModal={closeModal} />
      <Card>
        <CardBody>
          <CardTitle tag="h5" className="align-middle"> 사원 명부 </CardTitle>          
          {/* 검색어 입력 필드 및 버튼 */}
          <div className="d-flex mb-3">
            <Input
              type="text"
              name="search"
              placeholder="이름, 직위, 부서로 검색"
              value={searchQuery}
              onChange={handleSearchChange}
              className="me-2"
            />
            <Button onClick={handleSearch}>
              검색
            </Button>
          </div>

          <Table className="no-wrap mt-3 align-middle" responsive borderless>
            <thead>
              <tr>
                <th> 사원번호 </th>
                <th> 이 름 </th>
                <th> 직 위 </th>
                <th> 부 서 </th>
                <th> 입사일 </th>
              </tr>
            </thead>
            <tbody>
              {serverData.dtoList.map((member) => (
                <tr className="border-top" key={member.mno}>
                  <td>
                    <div className="d-flex align-items-center p-2">
                      <div className="ms-3">
                        <span
                          className="text-muted"
                          style={{ cursor: "pointer" }} // 커서 스타일을 직접 지정
                          onClick={() => openModal(member.mno)}
                        >
                          {member.mno}
                        </span>
                      </div>
                    </div>
                  </td>
                  <td>{member.name}</td>
                  <td>{changeRoleName(member.memberRole)}</td>
                  <td>{changeTeamName(member.team)} {member.teamName}</td>
                  <td>{member.start_date}</td>
                </tr>                
              ))}
              <tr>
                <td colSpan={'5'}>
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

export default OrgListComponent;
