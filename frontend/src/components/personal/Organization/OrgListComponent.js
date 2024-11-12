import { Card, CardBody, CardTitle, CardSubtitle, Table } from "reactstrap";
import { useEffect, useState } from "react";
import { list } from "../../../api/organizationAPI";

const OrgListComponent = () => {

    const initState = {
        antecedents: '',
        birth: '',
        children_count: '',
        education: '',
        marital_status: '',
        email:'',
        memberRoleList : [],
        mno : '',
        pw : '',
        start_date: '',
        mno: '',
        name: '',
        qualifications : '',
        sex : '',
        tel : ''   
      }

    const [MemberList, setMember] = useState({initState})
    const [fetching, setFetching ] = useState(false)   

    useEffect(()=> {
        setFetching(true)                
        // 데이터 가져오기            

        list().then(data => {            
            console.log('Fetched data: ', data.name)            
            setMember(data);                       
            setFetching(false);                                
        }).catch(error => {
            console.error('Error fetching data : ', error);
            setFetching(false);
        })
    }, [])
    //, []를 추가하는 이유는 무한 반복 막기 위함.   
    

    return (
        <div>
      <Card>
        <CardBody>
          <CardTitle tag="h5">Project Listing</CardTitle>
          <CardSubtitle className="mb-2 text-muted" tag="h6">
            Overview of the projects
          </CardSubtitle>

          <Table className="no-wrap mt-3 align-middle" responsive borderless>
            <thead>
              <tr>
                <th> 사원번호 </th>
                <th>  이  름 </th>
                <th> 직  위 </th>
                <th> 급  여 </th>
                <th> 입사일 </th>
              </tr>
            </thead>
            <tbody>
              
                <tr className="border-top">
                  <td>
                    <div className="d-flex align-items-center p-2">
                      {/* <img
                        src={tdata.avatar}
                        className="rounded-circle"
                        alt="avatar"
                        width="45"
                        height="45"
                      /> */}
                      <div className="ms-3">                        
                        <span className="text-muted">{member.mno}</span>
                        {/* 사원번호 */}
                      </div>
                    </div>
                  </td>
                  <td></td>
                  {/* 사원 이름 */}
                  <td>

                  </td>
                  {/* 직위 */}
                  <td>
                    {/* 급여 */}
                    </td>
                  <td>
                    {/* 입사일 */}
                    </td>
                </tr>
              
            </tbody>
          </Table>
        </CardBody>
      </Card>
    </div>

    )  
}


export default OrgListComponent;