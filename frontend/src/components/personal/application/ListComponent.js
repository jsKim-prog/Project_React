import { useEffect, useState } from "react";
import useCustomMove from "../../../hooks/useCustomList";
import useChangeData from "../../../hooks/useChangeData";
import { Button, Card, CardBody, CardTitle, Table } from "reactstrap";
import PageComponent from "../../common/PageComponent";
import { list } from "../../../api/applicationAPI";
import AppAddModal from "../Modal/AppAddModal";
import AppOneModal from "../Modal/AppOneModal";

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

const ListComponent = () => {
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
  const [selectedNo, setSelectedNo] = useState(null); // 선택된 입사지원 번호 상태 관리  
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달상태관리
  const [isModalOpen_register, setIsModalOpen_register] = useState(false);//입사지원서 입력 모달 상태 관리
  const { changeTeamName, changeJoinStatus } = useChangeData();

  const openModal = (no) => {
    if (no) {
      setSelectedNo(no); // 선택된 no 상태 저장
      setIsModalOpen(true); // 모달 열기
    }
  };

  const closeModal = () => {
    setIsModalOpen(false); // 모달 닫기
    setSelectedNo(null); // 선택된 mno 초기화
  };

  
  const openModal_register = () => {
    setIsModalOpen_register(true); // 모달 열기
    console.log("입사지원서입력창 모달 오픈");
  };

  const closeModal_register = () => {
    setIsModalOpen_register(false);
  };

  return (
    <div>
      <AppOneModal no={selectedNo} isOpen={isModalOpen} closeModal={closeModal}/>
      <AppAddModal isOpen={isModalOpen_register} closeModal={closeModal_register}/>
      <Card>
        <CardBody>
          <CardTitle tag="h5" className="align-middle"> 사원 명부 </CardTitle>                                
            <Table className="no-wrap mt-3" responsive borderless>
                <thead>
                <tr>
                    <td className="align-right" colSpan={6}>
                        <span className="ms-3 badge bg-success rounded-pill"
                            type="button" onClick={()=>openModal_register()}>
                            입사지원서입력
                        </span>                    
                    </td>
                </tr>
                <tr>
                    <th> 입사지원번호 </th>
                    <th>  이  름  </th>
                    <th> 이메일 </th>
                    <th> 전화번호 </th>
                    <th> 지원부서 </th>
                    <th> 지원결과 </th>                                  

                </tr>
                </thead>
                <tbody>
                {serverData.dtoList.map((member) => (
                    <tr className="border-top" key={member.no}>
                    <td>
                        <div className="d-flex align-items-center p-2">
                        <div className="ms-3">
                            <span
                            className="text-muted"
                            onClick={() => openModal(member.no)}
                            >
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
                    <td>{member.start_date}</td>
                    <td></td>
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

export default ListComponent;
