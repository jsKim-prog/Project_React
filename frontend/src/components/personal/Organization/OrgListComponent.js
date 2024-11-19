import { Card, CardBody, CardTitle, CardSubtitle, Table } from "reactstrap";
import { useEffect, useState } from "react";
import { list } from "../../../api/organizationAPI";
import OrgOneModal from "../Modal/OrgOneModal";
import useCustomMove from "../../../hooks/useCustomList";
import PageComponent from "../../common/PageComponent";
import useChangeData from "../../../hooks/useChangeData";

//기본 설정값
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
  //기초 배열사용해야 .map 오류 회피
  const [fetching, setFetching] = useState(false);
  const [serverData, setServerData] = useState({ ...pageState });

  useEffect(() => {
    setFetching(true);
    // 데이터 가져오기
    list({ page, size })
      .then((data) => {
        console.log('Fetched data: ', data);
        setServerData(data);
        setFetching(false);
      })
      .catch((error) => {
        console.error('Error fetching data : ', error);
        setFetching(false);
      });
  }, [page, size, refresh]);

  // 1인 선택시의 모달
  const [selectedMno, setSelectedMno] = useState(null); // 선택된 사원 번호 상태 관리
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달상태관리
  const { changeTeamName, changeRoleName } = useChangeData();

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
      <OrgOneModal
        mno={selectedMno}
        isOpen={isModalOpen}
        closeModal={closeModal}
      />
      <Card>
        <CardBody>
          <CardTitle tag="h5" className="align-middle"> 사원 명부 </CardTitle>          

          <Table className="no-wrap mt-3 align-middle" responsive borderless>
            <thead>
              <tr>
                <th> 사원번호 </th>
                <th>  이  름 </th>
                <th>
                   직  위                  
                </th>
                <th> 부  서 </th>
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
                <PageComponent serverData={serverData} movePage={moveToList} />
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
