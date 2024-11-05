import {Button, Card, CardBody, CardTitle, Col, Form, FormGroup, FormText, Input, Label, Row} from "reactstrap";
import useCustomMove from "../../hooks/useCustomMove";
import {useState} from "react";
import {postAdd} from "../../api/ProjectApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";


const initState = {
    title: "",
    content: "",
    startDate: "",
    dueDate: "",
    version: "1.0.0",
    status: "PROGRESS"
}
const RegisterComponent = () => {

    const [project, setProject] = useState({...initState});

    const {moveToList} = useCustomMove()

    // 서버와 통신 상태를 나타냄
    const [fetching, setFetching] = useState(false);

    // 결과를 보여줄 모달창
    const [result, setResult] = useState(null)

    const handleChangeProject = (e) => {
        project[e.target.name] = e.target.value
        setProject({...project})
    }
    const handleClickAdd = (e) => {
        const formData = new FormData();

        formData.append("title", project.title);
        formData.append("content", project.content);
        formData.append("startDate", project.startDate);
        formData.append("dueDate", project.dueDate);


        console.log(formData);

        setFetching(true);
        postAdd(formData).then(data => { // 서버에서 data 가져옴
            setFetching(false) // 서버 로딩 끝나면 false
            setResult(data); // Result 모달창에서 결과 집어넣기
        })

    }

    const closeModal = () => {
        setResult(null)
        moveToList({page: 1})
    }

    return (
            <div>
                {fetching ? <FetchingModal/> : <></>}
                <ResultModal
                    isOpen={result === "SUCCESS"}
                    content="다음 정보를 입력하세요"
                    callbackFn={closeModal}
                />
            <Row>
                <Col>
                    {/* --------------------------------------------------------------------------------*/}
                    {/* Card-1*/}
                    {/* --------------------------------------------------------------------------------*/}
                    <Card>
                        <CardTitle tag="h6" className="border-bottom p-3 mb-0">
                            <i className="bi bi-bell me-2"> </i>
                            프로젝트 생성
                        </CardTitle>
                        <CardBody>
                            <Form>
                                <FormGroup>
                                    <Label for="title">프로젝트 이름</Label>
                                    <Input
                                        id="title"
                                        name="title"
                                        placeholder="제목을 입력하세요"
                                        type="text"
                                        onChange={handleChangeProject}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Label for="startDate">시작일</Label>
                                    <Input
                                        id="startDate"
                                        name="startDate"
                                        placeholder="yyyy-MM-dd"
                                        type="date"
                                        onChange={handleChangeProject}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Label for="dueDate">마감일</Label>
                                    <Input
                                        id="dueDate"
                                        name="dueDate"
                                        placeholder="yyyy-MM-dd"
                                        type="date"
                                        onChange={handleChangeProject}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Label for="content">프로젝트 내용</Label>
                                    <Input id="content" name="content" type="textarea"
                                           onChange={handleChangeProject}/>

                                </FormGroup>

                                <Button type={"button"} onClick={handleClickAdd}>Submit</Button>
                            </Form>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}
export default RegisterComponent;