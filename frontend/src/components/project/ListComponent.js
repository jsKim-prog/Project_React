import {Button, Card, CardBody, CardSubtitle, CardTitle, Table} from "reactstrap";
import useCustomMove from "../../hooks/useCustomMove";
import {getList} from "../../api/ProjectApi";
import {useEffect, useState} from "react";
import PageComponent from "../common/PageComponent";
import {useNavigate} from "react-router-dom";

const initState = { // getList 메서드에서 반환할 PageResponseDTO 기본값 초기화 !!
    dtoList: [],
    pageNumList: [],
    pageRequestDTO: null,
    prev: false,
    next: false,
    totalCount: 0,
    prevPage: 0,
    nextPage: 0,
    totalPage: 0,
    current: 0
}
const ListComponent = () => {
    const navigate = useNavigate();

    const {page, size, refresh, moveToList, moveToRead} = useCustomMove()

    const [fetching, setFetching] = useState(false);

    const [serverData, setServerData] = useState(initState)

    const handleClickAdd = () => {
        navigate("/project/add");
    }



    useEffect(() => {
        setFetching(true)
        // getList 메서드 호출시 로딩
        getList({page, size}).then(data => { // data가져오면
            console.log(data) // 콘솔에 로그
            setServerData(data) // serverData에 삽입
            setFetching(false) // 로딩 종료
        })
        /* eslint-disable react-hooks/exhaustive-deps */
    }, [page, size, refresh])



    return (
        <div>
            <Card>
                <CardBody>
                    <CardTitle tag="h5" className="d-flex justify-content-between align-items-center">프로젝트 리스트
                        <Button color="primary" onClick={handleClickAdd} >새 프로젝트 생성</Button></CardTitle>

                    <Table className="no-wrap mt-3 align-middle" responsive borderless>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>시작일</th>
                            <th>마감일</th>
                            <th>상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        {serverData.dtoList.map((project, index) => (
                            <tr key={project.id} className="border-top" onClick={() => moveToRead(project.id)}>
                                <td>
                                    <div className="d-flex align-items-center p-2">
                                        <div className="ms-3">
                                            <h6 className="mb-0">{index}</h6>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div className="ms-3">
                                        <h6 className="mb-0">{project.title}</h6>
                                    </div>
                                </td>
                                <td>
                                    <div className="ms-3">
                                        <h6 className="mb-0">{project.startDate}</h6>
                                    </div>
                                </td>
                                <td>
                                    <div className="ms-3">
                                        <h6 className="mb-0">{project.dueDate}</h6>
                                    </div>
                                </td>
                                <td>
                                    <div className="ms-3">
                                        <h6 className="mb-0">{project.status}</h6>
                                    </div>
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
export default ListComponent;