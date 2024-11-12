import { Card, CardBody, CardTitle, CardSubtitle, Table } from "reactstrap";
import { useEffect, useState } from "react";
import { list } from "../../../api/organizationAPI";
import FetchingModal from "../../common/FetchingModal";
import useCustomMove from "../../../hooks/useCustomList";
import PageComponent from "../../common/PageComponent";

//기본 설정값
const pageState = {
    dtoList:[],
    pageNumList:[],
    pageRequestDTO: null,
    prev: false,
    next: false,
    totalCount: 0,
    prevPage: 0,
    nextPage: 0,
    totalPage: 0,
    current: 0
}

const initState = {
    antecedents: '',
    birth: '',
    children_count: '',
    education: '',
    marital_status: '',
    email:'',
    memberRole : '',
    mno : '',
    pw : '',
    start_date: '',
    mno: '',
    name: '',
    qualifications : '',
    sex : '',
    tel : ''   
  }
const OrgListComponent = () => {
    const {page, size, moveToList, refresh, moveToRead} = useCustomMove()
    const [memberList, setMember] = useState([{...initState}])
    //기초 배열사용해야 .map 오류 회피
    const [fetching, setFetching] = useState(false)   
    const [serverData, setServerData] = useState({...pageState})

    useEffect(()=> {
        setFetching(true)                
        // 데이터 가져오기            
        list({page,size}).then(data => {            
            console.log('Fetched data: ', data)
            console.log(Array.isArray(data))            
            setServerData(data);                       
            setFetching(false);                                
        }).catch(error => {
            console.error('Error fetching data : ', error);
            setFetching(false);
        })
    }, [page, size, refresh])
    //, []를 추가하는 이유는 무한 반복 막기 위함.   
    

    return (
        <div>

          

      <Card>
        <CardBody>
          <CardTitle tag="h5" className="align-middle"> 사원 명부 </CardTitle>
          <CardSubtitle className="mb-2 text-muted align-middle" tag="h6">            
            사원 명부 
          </CardSubtitle>

          <Table className="no-wrap mt-3 align-middle" responsive borderless>
            <thead>
              <tr>
                <th> 사원번호 </th>
                <th>  이  름 </th>
                <th> 직  위 </th>
                <th> e-mail </th>
                <th> 입사일 </th>
              </tr>
            </thead>
            <tbody>
              {serverData.dtoList.map((member) => (   
                <tr className="border-top">
                  <td>
                    <div className="d-flex align-items-center p-2">
                      {/* <img
                        src=사진 추가시 사용하자
                        className="rounded-circle"
                        alt="avatar"
                        width="45"
                        height="45"
                      /> */}
                      <div className="ms-3">                        
                        <span className="text-muted">
                            {member.mno}
                        </span>
                        {/* 사원번호 */}
                      </div>
                    </div>
                  </td>
                  <td>
                    {member.name}
                    {/* 사원 이름 */}
                  </td>

                  <td>
                    {member.memberRole}
                    {/* 직위 */}
                  </td>                  
                  <td>
                    {member.email}
                    </td>
                  <td>
                    {member.start_date}
                    {/* 입사일 */}
                    </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </CardBody>
      </Card>
      <PageComponent serverData={serverData} movePage={moveToList}></PageComponent>
    </div>


    )  
}


export default OrgListComponent;