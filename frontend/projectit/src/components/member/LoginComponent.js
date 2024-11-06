import { useState } from "react";
import { Card, CardTitle, Col, Row, Table } from "reactstrap";
import useCustomLogin from "../../hooks/useCustomLogin";
//import { useDispatch } from "react-redux";
//import { login } from "../../slices/loginSlice";

const initState = {
  email: '',
  pw:''
}

const LoginComponent = () => {

  const [loginParam, setLoginParam] = useState({...initState})

  const {doLogin, moveToPath} = useCustomLogin()

  //const dispatch = useDispatch()

  const handleChange = (e) => {
    loginParam[e.target.name] = e.target.value

    setLoginParam({...loginParam})
  }
  const handleClickLogin = (e) => {
    // dispatch(login(loginParam)) //동기화된 호출
    //비동기 호출
       doLogin(loginParam) //loginSlice의 비동기 호출
          .then(data => {
          console.log("after unwrap.....")
          console.log(data)
          if(data.error){
              alert("이메일과 패스워드를 다시 확인하세요.")
          } else {
              alert("로그인 성공")
              moveToPath('/') //뒤로 가기 했을 때 로그인 화면을 볼수 없음
          }

      })
  }


    return (
      
      <Row>        
        {/* --------------------------------------------------------------------------------*/}
        {/* table-2*/}
        {/* --------------------------------------------------------------------------------*/}
        <Col lg="12">
          <Card>
            <CardTitle tag="h6" className="border-bottom p-3 mb-0">              
              로그인 페이지 입니다.
            </CardTitle>
                <Table striped align="center"size="100px" >
                  <thead>
                
                  <tr>
                    <th>ID</th>
                    <th>
                        <input
                          type="text"
                          name="email"
                          value={loginParam.email}
                          onChange={handleChange}
                          placeholder="example@email.com"></input>
                        </th>                    
                  </tr>
                
                
                  <tr>
                    <th>Password</th>
                    <td>
                    <input
                      type="password"
                      name="pw"
                      value={loginParam.pw}
                      onChange={handleChange}
                      placeholder="password"
                      ></input>
                    </td>                    
                  </tr>

                  <tr>
                    <th colSpan={2}>                                                          
                    <button onClick={handleClickLogin}>로그인</button>
                    <button>정보찾기</button>
                    <button>회원가입</button>
                    </th>
                  </tr>
                  </thead>
                </Table>
            
            </Card>
        </Col>
      </Row>
    );
}
export default LoginComponent;