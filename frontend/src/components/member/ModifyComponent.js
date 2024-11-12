import { useEffect, useState } from "react";
import {
    Card,
    Row,
    Col,
    CardTitle,
    CardBody,
    FormGroup,
    Label,
    Input,
  } from "reactstrap";
import FetchingModal from "../common/FetchingModal";
import { modifyMember, statusRead } from "../../api/memberApi";
import { getCookie } from "../../util/cookieUtil";
import useCustomLink from "../../hooks/useCustomLink";



  const ModifyComponent = () => {

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

    
    const [memberS, setMember] = useState(initState)       
    const [fetching, setFetching ] = useState(false)           
    const token = getCookie('member'); //member라는 이름을 가진 쿠키 추출
    const {moveToMain} = useCustomLink();

    useEffect(()=> {
        setFetching(true)                
        // 데이터 가져오기
        
        console.log(token.mno);              
        const mno = token.mno;

        statusRead(mno).then(data => {            
            console.log('Fetched data: ', data.name)
            console.log(mno);
            setMember(data);           
            console.log(data);            
            console.log(memberS)           
            setFetching(false);                                
        }).catch(error => {
            console.error('Error fetching data : ', error);
            setFetching(false);
        })
    }, [])
    //, []를 추가하는 이유는 무한 반복 막기 위함.

    useEffect(()=>{
        console.log("멤버 업데이트 change memberS :  ", memberS);
    }, [memberS]);   
        
    const handleChange = (e) => {        
        const { name, value } = e.target;   //input, name. value추출        
        setMember((prevState) => ({
            ...prevState,   // 이전 상태 복사
            [name]: value,   // 해당 필드 업데이트            
        }));
       };
    
    const handleClickModify = () => {
        
        console.log(memberS);
        console.log(token.mno);
        
        modifyMember(memberS, token.mno)
        .then(data => {                        
            console.log("modify success")            
            if(data.error){
                alert("오류가 발생되었습니다. 인사과에 문의해주세요")
            } else {
                alert("정보 수정 성공")
                moveToMain()               
            }
        });
      // 수정데이터 서버 전송 로직 자리  
    };

    return (
      <Row>
        {fetching? <FetchingModal /> : (
        <Col>
          {/* --------------------------------------------------------------------------------*/}
          {/* Card-1*/}
          {/* --------------------------------------------------------------------------------*/}
          <Card>
            <CardTitle tag="h6" className="border-bottom p-3 mb-0">
              <i className="bi bi-bell me-2"> </i>
                사원 정보 수정 페이지
            </CardTitle>
            <CardBody>
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <Label for="Email">Email</Label>
                        </td>
                        <td>
                            <Input
                                id="email"
                                name="email"
                                placeholder="example@mail.com"                                
                                type="email"                  
                                value={memberS.email || ""}
                                onChange={handleChange}                                
                            />
                        </td>

                        <td>
                             <Label for="Name" >Name</Label>
                        </td>
                        <td>
                            <Input
                                id="Name"
                                name="name"
                                placeholder="name"
                                type="text"
                                value={memberS.name || ""}
                                readOnly
                                
                            />                            
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <Label for="password">Password</Label>
                        </td>
                        <td>
                            <Input
                                id="password"
                                name="password"
                                placeholder="password"
                                type="password"
                                value={memberS.pw}
                                onChange={handleChange}
                            />
                        </td>

                        <td>
                            <Label for="Password2">Password Check</Label>
                        </td>
                        <td>
                            <Input
                                id="Password2"
                                name="password2"
                                placeholder="password"
                                type="password"
                                value={memberS.pw}
                                onChange={handleChange}
                            />
                        </td>
                    </tr>

                    <tr>
                    <td>
                            <Label for="Birth">Birth</Label>
                        </td>
                        <td>
                            <Input
                                id="Birth"
                                name="birth"
                                placeholder="birth"
                                type="date"
                                value={memberS.birth || ""}   
                                readOnly
                            />
                        </td>

                        <td>
                            <Label for="Tel">Tel</Label>
                        </td>
                        <td>
                            <Input
                                id="Tel"
                                name="tel"
                                placeholder="01000000000"
                                type="tel"
                                value={memberS.tel || ""}
                                onChange={handleChange}   
                            />
                        </td>
                        
                    </tr>

                    
                    <tr>
                        <td>
                            <Label for="Sex">Sex</Label>
                        </td>
                        <td>
                            <Input
                                id="Male"
                                name="sex"                                
                                value="Male"
                                type="radio" 
                                checked={memberS.sex === 'Male' || ""}
                                onChange={handleChange}
                            />
                            <Label for="Male">Male</Label>

                            <Input
                                id="Female"
                                name="sex"
                                value="Female"
                                type="radio"
                                checked={memberS.sex === 'Female' || ""}
                                onChange={handleChange}                    
                             />
                            <Label for="Female">Female</Label>
                        </td>
                        <td>
                            <Label> 최종학력 </Label>
                        </td>
                        <td>
                            <Input
                                id="education"
                                name="education"
                                type="select"
                                value={memberS.education || ""}
                                onChange={handleChange}
                            >
                                <option> 중학교 졸업 </option>
                                <option> 직업학교 졸업 </option>
                                <option> 고등학교 졸업</option>
                                <option> 전문대 졸업 </option>
                                <option> 4년제 대학 졸업 </option>
                                <option> 대학원 졸업 </option>                     


                            </Input>

                        </td>
                    
                    </tr>

                    <tr>
                        <td>
                            <Label for="marital_status">혼인유무</Label>
                        </td>
                        <td>
                            <Input
                                id="기혼"
                                name="marital_status"
                                value="기혼"                                
                                type="radio"
                                checked={memberS.marital_status === '기혼' || ""}
                                onChange={handleChange}
                            />
                            <Label for="기혼">기혼</Label>

                            <Input
                                id="미혼"
                                name="marital_status" 
                                value="미혼"                                                               
                                type="radio" 
                                checked={memberS.marital_status === '미혼' || ""}                           
                                onChange={handleChange}
                             />
                            <Label for="미혼">미혼</Label>
                        </td>

                        <td>
                            <Label> 자녀 수 </Label>
                        </td>
                        
                        <td>
                            <Input id="children_count" name="children_count" type="select"
                            value={memberS.children_count || ""} onChange={handleChange}>
                                <option>0</option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </Input>
                        </td>
                        

                    </tr>

                    <tr>
                        <td colSpan={4}>
                        <label> 보유 자격증 </label>
                        </td>
                    </tr>
                    
                    <tr>
                        <td colSpan={4}>
                        <FormGroup>
                            <Input id="qualifications" name="qualifications"
                            value={memberS.qualifications || ""} onChange={handleChange}/>
                        </FormGroup>
                        </td>
                    </tr>

                    <tr>
                        <td colSpan={4}>
                        <label> 경력 </label>
                        </td>
                    </tr>
                    
                    <tr>
                        <td colSpan={4}>
                        <FormGroup>
                            <Input id="antecedents" name="antecedents"
                            value={memberS.antecedents || ""} onChange={handleChange} />
                        </FormGroup>
                        </td>
                    </tr>

                    <tr>
                        <td colSpan={4}>
                            <button type="button" onClick={handleClickModify}> 사원정보 수정하기 </button>
                        </td>
                    </tr>

                    </tbody>
                </table>
              
            </CardBody>
          </Card>
        </Col>
        )}
      </Row>
    );
  };
  
  export default ModifyComponent;
  