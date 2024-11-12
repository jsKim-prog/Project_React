import { useEffect, useRef, useState } from "react";
import { Button, Card, CardBody, CardTitle, Form, FormGroup, FormText, Input, InputGroup, Label } from "reactstrap";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useDistMove from "../../hooks/useDistMove";
import { getOneInfo, registAsset } from "../../api/LicenseApi";
import LicenseInfoModal from "../distModal/LicenseInfoModal";
import { addDays, addMonths, addYears, format } from "date-fns";
const currentDate = format(new Date, 'yyyy-MM-dd'); //현재시간
const initState = {//AssetLicenseDTO
    type: '',
    contractStatus: "NEW",
    contractDate: currentDate,
    expireDate: currentDate,
    contractCount: 0,
    comment: '',
    licenseId: 0,
    files: [],
    fileCount: 0,
}

const infoInitState = {//InfoLicenseDTO --관련 라이선스 불러오기(getOne)
    lno: 0,
    rightName: '',
    version: '',
    purpose: '',
    copyrightHolder: '',
    price: 0,
    priceUnit: 'YEAR',
    maxUserCount: 0,
    contact: '-'
}

const LicenseAssetRequestComponent = () => {
    const [licenseAsset, setLicenseAsset] = useState({ ...initState }); //등록용 객체 set용
    const uploadFile = useRef(); //첨부파일 처리
    const [fetching, setFetching] = useState(false); //진행모달(로딩)
    const [result, setResult] = useState(null); //결과모달창
    const [listModalOpen, setListModalOpen] = useState(false); //info 리스트 모달창 열림/닫힘 제어상황
    const { moveToList } = useDistMove(); //페이지 이동   
    const [infoResult, setInfoResult] = useState({ ...infoInitState }); //modal에서 받은 값 세팅
    const [lno, setLno] = useState(0); //modal에서 받은 lno
    const [exDate, setExDate] = useState(null); //날짜입력시
    
    useEffect(()=>{
        if(infoResult.lno !== 0 && licenseAsset.contractDate !== ''){
            exDate = calculateExdate();
            setExDate(exDate);
        }

    },[infoResult, exDate, licenseAsset])



    //입력값 변경시 객체 값 세팅
    const handleChangeLicenseAsset = (e) => {
        licenseAsset[e.target.name] = e.target.value
        setLicenseAsset({ ...licenseAsset })
    }

    const calculateExdate = () => {

        let unit = infoResult.priceUnit;
        let startDate = format(licenseAsset.contractDate, 'yyyy-MM-dd');
        let count = licenseAsset.contractCount;
        switch (unit) {
            case "YEAR", "PERSON" : return addYears(startDate, 1);
            case "MONTHLY" : return addMonths(startDate, count);
        }

    }


    //전송용 formdata->axios
    const handleClickAdd = (e) => {
        const files = uploadFile.current.files;
        const formData = new FormData();
        for (let i = 0; i < files.length; i++) {
            formData.append("files", files[i]);
        }

        formData.append("type", licenseAsset.type);
        formData.append("contractStatus", licenseAsset.contractStatus);
        formData.append("contractDate", licenseAsset.contractDate);
        formData.append("expireDate", licenseAsset.expireDate);
        formData.append("contractCount", licenseAsset.contractCount);
        formData.append("comment", licenseAsset.comment);
        formData.append("licenseId", infoResult.lno);

        formData.append("fileCount", files.length);

        console.log("등록 : " + formData)
        setFetching(true)
        registAsset(formData).then(data => {
            setFetching(false)
            setResult(data.result)
        });
    }

    //모달창 닫기(결과확인 후)
    const closeModal = () => {
        setResult(null)
        moveToList({ page: 1 })
    }

    //모달창 열기(info모달)
    const infoModalOpen = (e) => {
        e.preventDefault();
        setListModalOpen(true);
    }

    //모달창 닫기(info모달)
    const infoModalClose = (lno) => {
        setLno(lno);
        console.log("lno : " + lno);
        setListModalOpen(false);
        setFetching(true)
        if (lno === null || lno === 0) {
            alert("선택한 상품이 없습니다. 상품을 선택해 주세요.")
        } else {
            getOneInfo(lno).then(data => {
                setInfoResult(data)
                setFetching(false)
            });
        }
    }



    return (
        <div>
            {fetching ? <FetchingModal /> : <></>}
            {result ? <ResultModal
                isOpen={result > 0 || result !== ''}
                content={"등록이 완료되었습니다."}
                callbackFn={closeModal}></ResultModal> : <></>}
            <Card>
                <CardTitle tag="h5" className="d-flex justify-content-between align-items-center border-bottom p-3 mb-0">
                    라이선스 사용요청
                </CardTitle>
                <CardBody>
                    <Form>
                        <FormGroup>
                            <Label for="rightName">상품명 <span><Button className="btn" color="info" onClick={(e) => infoModalOpen}>상품찾기</Button></span></Label>
                                {infoResult.lno === 0 ?
                                    <Input
                                        id="rightName"
                                        name="rightName"
                                        placeholder="[상품찾기]를 클릭하여 상품을 선택하세요."
                                        type="text"
                                    />
                                    : <Input
                                        id="rightName"
                                        name="rightName"
                                        type="text"
                                        value={infoResult.rightName}
                                    />}                     
                        </FormGroup>
                        <FormGroup>
                            <Label for="type">계약구분</Label>
                            <Input id="type" name="type" type="select" onChange={handleChangeLicenseAsset}>
                                <option value={"NEW"}>신규</option>
                                <option value={"EXTENSION"}>연장</option>
                                <option value={"RENEWAL"}>재계약</option>
                                <option value={"CANCEL"}>해지</option>
                            </Input>
                        </FormGroup>
                        <FormGroup>
                            <Label for="purpose">용도</Label>
                            <Input id="purpose" name="purpose" type="select" onChange={handleChangeLicenseAsset}>
                                <option value={"programming"}>프로그래밍</option>
                                <option value={"design"}>디자인</option>
                                <option value={"documemt"}>문서작성</option>
                                <option value={"management"}>경영/회계</option>
                                <option value={"network"}>네트워크/보안</option>
                                <option value={"etc"}>기타</option>
                            </Input>
                        </FormGroup>
                        <FormGroup>
                            <Label for="contractCount">구입/계약 개수</Label>
                            <Input
                                id="contractCount"
                                name="contractCount"
                                placeholder="0"
                                type="number"
                                onChange={handleChangeLicenseAsset}
                            />
                        </FormGroup>
                        <FormGroup>
                            <Label for="contractDate">구입일</Label>
                            <Input
                                id="contractDate"
                                name="contractDate"
                                placeholder="yyyy-MM-dd"
                                type="date"
                                onChange={handleChangeLicenseAsset}
                            />
                        </FormGroup>
                        <FormGroup>
                            <Label for="expireDate">만료일</Label>
                            <Input
                                id="expireDate"
                                name="expireDate"
                                placeholder="yyyy-MM-dd"
                                type="date"
                                value={exDate}
                                readOnly
                            />
                        </FormGroup>
                        <FormGroup>
                            <Label for="comment">비고</Label>
                            <Input
                                id="comment"
                                name="comment"
                                placeholder="자유입력"
                                type="textarea"
                                onChange={handleChangeLicenseAsset}
                            />
                        </FormGroup>
                        <FormGroup>
                            <Label for="files">첨부파일</Label>
                            <Input ref={uploadFile} id="files" name="files" type="file" multiple />
                            <FormText>
                                *안내문-수정예정
                            </FormText>
                        </FormGroup>
                        <div className="text-center">
                            <Button className="btn" color="primary" onClick={(e) => handleClickAdd}>등록하기</Button></div>
                            <LicenseInfoModal isOpen={listModalOpen} callbackFn={infoModalClose} resultLno={lno}></LicenseInfoModal>
                    </Form>
                </CardBody>
            </Card>
        </div>
    );
}
export default LicenseAssetRequestComponent;