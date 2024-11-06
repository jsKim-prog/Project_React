import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
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
import { statusRead } from "../../api/memberApi";
import { getCookie } from "../../util/cookieUtil";
import useCustomLogin from "../../hooks/useCustomLogin";

  const initState = {
    email: '',
    pw: '',    
    name: '',    
    birth: '',    
    tel: '',    
    sex: '',    
    marital_status: '',    
    children_count: '',    
    qaulifications: '',    
    education: '',
    antecedents: ''
  }
  const memberInfo = getCookie("member")

 


  
  const ModifyComponent = () => {

    const [fetching, setFetching] = useState(false)

    console.log(memberInfo.mno)
    
    
       
    const [modifyMember, setModifyMember] = useState({...initState})

    console.log(modifyMember)    

    useEffect(()=>{
        setFetching(true)

        statusRead(memberInfo.mno).then(data => {
            setModifyMember(data)
            setFetching(false)
        })    
    }, [memberInfo.mno])    
    
    const handleChange = (e) => {
        modifyMember[e.target.name] = e.target.value
    
        setModifyMember({...modifyMember})
       }
    
    const handleClickModify = (e) => {
        console.log(modifyMember);
        modifyMember(modifyMember);
    }






    return (
      <Row>
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
                    <thead>
                    <tr>
                        <td>
                            <Label for="Email">Email</Label>
                        </td>
                        <td>
                            <Input
                                id="email"
                                name="email"
                                type="email"
                                value={modifyMember.email}
                            />
                        </td>

                        <td>
                             <Label for="Name" >Name</Label>
                        </td>
                        <td>
                            <Input
                                readOnly
                                id="Name"
                                name="name"
                                placeholder="name"
                                type="text"
                            
                            />                            
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <Label for="Password">Password</Label>
                        </td>
                        <td>
                            <Input
                                id="Password1"
                                name="password1"
                                placeholder="password"
                                type="password"
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
                                name="male"                                
                                type="radio" 
                                readOnly
                            />
                            <Label for="Male">Male</Label>

                            <Input
                            id="Female"
                            name="male"                                
                            type="radio"
                            readOnly
                             />
                            <Label for="Male">Female</Label>
                        </td>
                        <td>
                            <Label> 최종학력 </Label>
                        </td>
                        <td>
                            <Input
                                id="education"
                                name="education"
                                type="select"
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
                                id="marital_status"
                                name="marital_status"                                
                                type="radio"
                            />
                            <Label for="기혼">기혼</Label>

                            <Input
                            id="marital_status"
                            name="marital_status"                                
                            type="radio"                            
                             />
                            <Label for="미혼">미혼</Label>
                        </td>

                        <td>
                            <Label> 자녀 수 </Label>
                        </td>
                        
                        <td>
                            <Input id="children_count" name="children_count" type="select">
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
                            <Input id="qualifications" name="qualifications" type="textarea" />
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
                            <Input id="antecedents" name="antecedents" type="textarea" />
                        </FormGroup>
                        </td>
                    </tr>

                    <tr>
                        <td colSpan={4}>
                            <button> 사원정보 수정하기 </button>
                        </td>
                    </tr>

                </thead>
                </table>
              
            </CardBody>
          </Card>
        </Col>
      </Row>
    );
  };
  
  export default ModifyComponent;
  