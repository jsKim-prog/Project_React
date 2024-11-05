import React, { useState, useEffect } from "react";
import { Button, FormGroup, Label, Input, Form } from "reactstrap";
import {getOne} from "../../api/ProjectApi";
import useCustomMove from "../../hooks/useCustomMove";

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

const ReadComponent = ({id}) => {

    const {moveToList, moveToModify} = useCustomMove()
    const [project, setProject] = useState(initState);

    useEffect(() => {
        getOne(id).then(data => {
            setProject(data)
        })
    }, [id])

    return (
        <div className="project-details">
            <h5>Project Details</h5>
            <Form>
                <FormGroup>
                    <Label>제목</Label>
                    <Input type="text" value={project.title} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>등록일</Label>
                    <Input type="text" value={project.regDate} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>수정일</Label>
                    <Input type="text" value={project.updateDate} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>시작일</Label>
                    <Input type="text" value={project.startDate} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>만기일</Label>
                    <Input type="text" value={project.dueDate} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>주요 내용</Label>
                    <Input type="textarea" value={project.content} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>진행 상태</Label>
                    <Input type="text" value={project.status} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <FormGroup>
                    <Label>Version</Label>
                    <Input type="text" value={project.version} readOnly style={{ backgroundColor: '#f0f0f0' }}/>
                </FormGroup>
                <div className="button-group">
                    <Button color="primary" className={"me-2"} onClick={() => moveToModify(project.id)}>Modify</Button>
                    <Button color="secondary" className={"me-2"} onClick={() => moveToList()}>List</Button>
                </div>
            </Form>
        </div>
    );

}

export default ReadComponent;