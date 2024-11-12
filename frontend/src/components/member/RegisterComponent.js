import { useState } from "react";
import {    Card,    Row,    Col,    CardTitle,    CardBody,       FormGroup,    Label,    Input  } from "reactstrap";
import { registerMember } from "../../api/memberApi";
import useCustomLogin from "../../hooks/useCustomLogin";

  const initState = {
    email: '',
    password: '',    
    name: '',    
    birth: '',    
    tel: '',    
    sex: '',    
    marital_status: '',    
    children_count: '0',    
    qualifications: '',    
    education: '',
    antecedents: ''
  }


  
  const RegisterComponent = () => {   

    const [registerParam, setRegisterParam] = useState(initState)

    const {moveToLogin} = useCustomLogin()

    const handleChange = (e) => {
        registerParam[e.target.name] = e.target.value    
        setRegisterParam({...registerParam})
       }
    
    const handleClickRegister = () => {
        console.log(registerParam);
        registerMember(registerParam).then(data => {
            if(data.error){
                alert("중복된 계정이 있습니다.")
            } else {
                alert("가입 성공")
                moveToLogin()
                

            }
        })


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
              회원 가입 페이지
            </CardTitle>
            <CardBody>
                <table>
                    <thead>
                    <tr>
                        <td>
                            <Label>Email</Label>
                        </td>
                        <td>
                            <Input
                                id="email"
                                name="email"
                                placeholder="example@mail.com"
                                type="email"
                                onChange={handleChange}
                            />
                        </td>

                        <td>
                             <Label>Name</Label>
                        </td>
                        <td>
                            <Input
                                id="name"
                                name="name"
                                placeholder="name"
                                type="text"
                                onChange={handleChange}
                            />                            
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <Label>Password</Label>
                        </td>
                        <td>
                            <Input
                                id="password"
                                name="password"
                                placeholder="password"
                                type="password"
                                onChange={handleChange}
                            />
                        </td>

                        <td>
                            <Label>Password Check</Label>
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
                            <Label>Birth</Label>
                        </td>
                        <td>
                            <Input
                                id="birth"
                                name="birth"
                                placeholder="birth"
                                type="date"
                                onChange={handleChange}
                            />
                        </td>

                        <td>
                            <Label>Tel</Label>
                        </td>
                        <td>
                            <Input
                                id="tel"
                                name="tel"
                                placeholder="01000000000"
                                type="tel"
                                onChange={handleChange}
                            />
                        </td>
                        
                    </tr>

                    
                    <tr>
                        <td>
                            <Label>Sex</Label>
                        </td>
                        <td>
                            <Input
                                id="sex"
                                name="sex"                                
                                type="radio" 
                                value={"Male"}
                                onChange={handleChange}
                            />
                            <Label for="Male">Male</Label>

                            <Input
                            id="sex"
                            name="sex"                                
                            type="radio"
                            value={"Female"}
                            onChange={handleChange}
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
                            <Label for="registerParam.marital_status">혼인유무</Label>
                        </td>
                        <td>
                            <Input
                                id="marital_status"
                                name="marital_status"                                
                                type="radio"
                                value={"기혼"}
                                onChange={handleChange}
                            />
                            <Label for="기혼">기혼</Label>

                            <Input
                            id="marital_status"
                            name="marital_status"                                
                            type="radio"    
                            value="미혼"
                            onChange={handleChange}                        
                             />
                            <Label for="미혼">미혼</Label>
                        </td>

                        <td>
                            <Label> 자녀 수 </Label>
                        </td>
                        
                        <td>
                            <Input id="children_count" name="children_count" type="select" onChange={handleChange} value={registerParam.children_count}>
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
                            <Input id="qualifications" name="qualifications" type="textarea" onChange={handleChange}/>
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
                            <Input id="antecedents" name="antecedents" type="textarea" onChange={handleChange}/>
                        </FormGroup>
                        </td>
                    </tr>

                    <tr>
                        <td colSpan={4}>
                            <button type="button" onClick={handleClickRegister}> 사원정보 등록하기 </button>
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
  
  export default RegisterComponent;
  