import React, { useState, useEffect } from "react";
import { Button, FormGroup, Label, Input, Form } from "reactstrap";
import {deleteOne, getOne, putOne} from "../../api/ProjectApi";
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";

const initState = {
    id:0,
    title:"",
    regDate:"",
    updateDate:"",
    startDate:"",
    dueDate:"",
    content:"",
    status:"",
    version:""
}

const UpdateComponent = ({id}) => {

    const {moveToList} = useCustomMove()
    const [result, setResult] = useState(null)
    const [project, setProject] = useState(initState);
    const handleChangeProject = (e) => {
        project[e.target.name] = e.target.value
        setProject({...project})
    }
    useEffect(() => {
        getOne(id).then(data => {
            setProject(data)

        })
    }, [id])

    const handleClickModify = () => {
        putOne(project, id).then(data => {
            setResult(data)
        })
    }

    const handleClickDelete = () => {
        deleteOne(id).then(data => {
            setResult(data)
        })
    }

    const closeModal = () => {
        setResult(null)
        moveToList({page:1});
    }

    return (
        <div className="project-details">

            <h5>Project Details</h5>
            <ResultModal
                isOpen={result === "SUCCESS" || result === "DELETE"}
                content={result === "SUCCESS"
                    ? "프로젝트 수정이 완료되었습니다."
                    : "프로젝트 삭제가 완료되었습니다."}
                callbackFn={closeModal}
            />
            <Form>
                <FormGroup>
                    <Label>제목</Label>
                    <Input
                        type="text"
                        name="title"
                        value={project.title}
                        onChange={handleChangeProject}
                    />
                </FormGroup>
                <FormGroup  className={""}>
                    <Label>시작일</Label>
                    <Input
                        type="date"
                        name="startDate"
                        value={project.startDate}
                        onChange={handleChangeProject}
                        style={{ backgroundColor: '#f0f0f0' }}
                        readOnly
                    />
                </FormGroup>
                <FormGroup>
                    <Label>만기일</Label>
                    <Input
                        type="date"
                        name="dueDate"
                        value={project.dueDate}
                        onChange={handleChangeProject}
                    />
                </FormGroup>
                <FormGroup>
                    <Label>주요 내용</Label>
                    <Input
                        type="textarea"
                        name="content"
                        value={project.content}
                        onChange={handleChangeProject}
                    />
                </FormGroup>
                <FormGroup>
                    <Label>진행 상태</Label>
                    <Input
                        type="text"
                        name="status"
                        value={project.status}
                        onChange={handleChangeProject}
                    />
                </FormGroup>
                <FormGroup>
                    <Label>Version</Label>
                    <Input
                        type="text"
                        name="version"
                        value={project.version}
                        onChange={handleChangeProject}
                    />
                </FormGroup>
                <div className="button-group">
                    <Button color="primary" className={"me-2"} onClick={handleClickModify}>Modify</Button>
                    <Button color="danger" className={"me-2"} onClick={handleClickDelete}>Delete</Button>
                    <Button color="secondary" className={"me-2"} onClick={() => moveToList()}>List</Button>
                </div>
            </Form>
        </div>
    );

}

export default UpdateComponent;