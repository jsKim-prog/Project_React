import { useState } from "react";
import { Alert, Button, Card, CardBody, CardTitle, Form, FormGroup, FormText, Input, InputGroup, Label } from "reactstrap";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useDistMove from "../../hooks/useDistMove";
import { registAsset } from "../../api/LicenseApi";
import LicenseInfoModal from "../distModal/LicenseInfoModal";
import { addMonths, addYears, format } from "date-fns";

const currentDate = format(new Date(), 'yyyy-MM-dd'); //현재시간
const initState = {//AssetLicenseDTO
    rightType: "[구입]사용권",
    contractStatus: "신규계약",
    usePurpose:"문서/사무",
    contractDate: currentDate,
    expireDate: currentDate,
    contractCount: 0,
    totalPrice: 0,
    comment: '',
    expireYN: false,
    licenseId: 0,
    files: [],
    fileCount: 0,
}

const infoInitState = {//InfoLicenseDTO --관련 라이선스 불러오기
    lno: 0,
    rightName: '',
    price: 0,
    priceUnit: 'YEAR',
    maxUserCount: 0,
}



const LicenseAssetRequestComponent = () => {
    let checkModal = 0;
    const [licenseAsset, setLicenseAsset] = useState({ ...initState }); //등록용 객체 set용
    //const uploadFile = useRef(); //첨부파일 처리
    const [inputFiles, setInpuFiles] = useState([]); //첨부파일 데이터 보관
    const [fetching, setFetching] = useState(false); //진행모달(로딩)
    const [result, setResult] = useState(null); //결과모달창
    const [listModalOpen, setListModalOpen] = useState(false); //info 리스트 모달창 열림/닫힘 제어상황
    const { moveToList } = useDistMove(); //페이지 이동   
    const [infoResult, setInfoResult] = useState({ ...infoInitState }); //modal에서 받은 값 세팅
    const [checkModalClick, setCheckModalClick] = useState(checkModal); //모달 열렸었는지 체크
    const [checkInsert, setCheckInsert] = useState(false);  //만료일 계산기 활성화 위한 입력 체크

    //입력값 변경시 객체 값 세팅(licenseAsset)
    const handleChangeLicenseAsset = (e) => {
        licenseAsset[e.target.name] = e.target.value
        setLicenseAsset({ ...licenseAsset })
        setCheckInsert(true);
    }
    

    //입력값 변경시 객체 값 세팅(infoResult)
    const handleChangeLicenseInfo = (e) => {
        infoResult[e.target.name] = e.target.value
        setInfoResult({ ...infoResult });
        setCheckInsert(true);
    }

    //전송용 formdata->axios
    const handleClickAdd = (e) => {
        e.preventDefault();
        console.log("등록하기 실행");
        console.log("files : "+inputFiles);
        const formData = new FormData();
       for (let i = 0; i < inputFiles.length; i++) {
            formData.append("files", inputFiles[i]);
        }

        formData.append("rightType", licenseAsset.rightType); 
        formData.append("contractStatus", licenseAsset.contractStatus);
        formData.append("usePurpose", licenseAsset.usePurpose);
        formData.append("contractDate", licenseAsset.contractDate);
        formData.append("expireDate", licenseAsset.expireDate);
        formData.append("contractCount", licenseAsset.contractCount);
        formData.append("totalPrice", licenseAsset.totalPrice);
        formData.append("comment", licenseAsset.comment);
        formData.append("licenseId", infoResult.lno);
        formData.append("fileCount", inputFiles.length);
        

       // console.log("등록 : " + JSON.stringify(formData));
        console.log("등록 : " + formData.get("fileCount"));
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
    const infoModalOpen = () => {
        console.log("버튼작동");
        setListModalOpen(true);
    }

    //값 받아 처리 ->모달창 닫기(info모달)
    const handleInfoSave = ({ selectInfo }) => {
        setFetching(true);
        setCheckModalClick(checkModalClick + 1);
        // console.log("checkModal : "+checkModal);
        // console.log("checkModalClick : "+checkModalClick);
        setInfoResult({ ...selectInfo }); //받은값 세팅
        console.log("받은객체 : " + selectInfo.lno);
        console.log("객체 결과 : " + infoResult.lno);
        setListModalOpen(!listModalOpen);
        setFetching(false);
    }

    const changeExDate = (contractDate, priceUnit) => {
       // console.log(licenseAsset.contractDate);
        let expireDate = new Date();
        switch (priceUnit) {
            case "년":
                expireDate = addYears(contractDate, 1);
                break;
            case "월":
                expireDate = addMonths(contractDate, 1);
                break;
            case "인":
                expireDate = addYears(contractDate, 1);
                break;
        }
        console.log(expireDate);
        return format(expireDate, 'yyyy-MM-dd');
    }

    const handleFileChange = (e)=>{           
        setInpuFiles(Array.from(e.target.files));
        console.log(inputFiles);
    }




    return (
        <div>
            {fetching ? <FetchingModal></FetchingModal> : <></>}
            {result ? <ResultModal
                isOpen={result > 0 || result !== ''}
                content={"등록이 완료되었습니다."}
                callbackFn={closeModal}></ResultModal> : <></>}
                <Form>
            <Card>
                <CardTitle tag="h5" className="d-flex justify-content-between align-items-center border-bottom p-3 mb-0">
                    라이선스 사용요청<span>
                        <Button type="button" className="btn" color="info" onClick={infoModalOpen}>상품찾기</Button>
                        <LicenseInfoModal isOpen={listModalOpen} callbackFn={handleInfoSave} ></LicenseInfoModal>
                    </span>
                </CardTitle>
                <CardBody>
                    
                        {checkModalClick > 0 ?
                            <FormGroup>
                                <Label for="rightName">상품명 </Label>
                                <Input
                                    id="rightName"
                                    name="rightName"
                                    placeholder="[상품찾기]를 클릭하여 상품을 선택하세요."
                                    value={infoResult.rightName}
                                    type="text"
                                    readOnly
                                    onChange={handleChangeLicenseInfo}
                                />
                                <Input id="lno" name="lno" type="hidden" value={infoResult.lno} onChange={handleChangeLicenseInfo}></Input>
                                <Label for="priceGroup" className="mt-3">금액/단위</Label>
                                <InputGroup id="priceGroup">
                                    <Input
                                        id="price"
                                        name="price"
                                        placeholder="00"
                                        type="number"
                                        value={infoResult.price}
                                        readOnly
                                        onChange={handleChangeLicenseInfo}
                                    />
                                    <span><Input
                                        id="priceUnit"
                                        name="priceUnit"
                                        type="text"
                                        value={infoResult.priceUnit}
                                        onChange={handleChangeLicenseInfo}
                                        readOnly></Input></span>
                                </InputGroup>
                            </FormGroup>
                            :
                            <Alert color="primary">
                                [상품찾기]를 클릭하여 상품을 선택하세요.
                            </Alert>}
                </CardBody>
            </Card>
            <Card>
                <CardBody>
                <FormGroup>
                            <Label for="rightType">권리유형</Label>
                            <Input id="rightType" name="rightType" type="select" onChange={handleChangeLicenseAsset}>
                                <option>오픈소스</option>
                                <option>[구입]사용권</option>
                                <option>[보유]저작권</option>
                                <option>[보유]특허</option>
                            </Input>
                        </FormGroup>
                        <FormGroup>
                            <Label for="contractStatus">계약구분</Label>
                            <Input id="contractStatus" name="contractStatus" type="select" onChange={handleChangeLicenseAsset}>
                                <option>신규계약</option>
                                <option>연장계약</option>
                                <option>재계약</option>
                                <option>계약해지</option>
                            </Input>
                        </FormGroup>
                        <FormGroup>
                            <Label for="usePurpose">용도</Label>
                            <Input id="usePurpose" name="usePurpose" type="select" onChange={handleChangeLicenseAsset}>
                                <option>문서/사무</option>
                                <option>개발</option>
                                <option>디자인</option>
                                <option>경영/회계</option>
                                <option>네트워크/보안</option>
                                <option>기타</option>
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
                            <Label for="totalPrice">총 금액</Label>
                            <Input
                                id="totalPrice"
                                name="totalPrice"
                                placeholder="0"
                                type="number"
                                value={ licenseAsset.contractCount * infoResult.price}
                                onChange={handleChangeLicenseAsset}
                                readOnly
                            />
                        </FormGroup>
                        <FormGroup>
                            <Label for="contractDate">구입일</Label>
                            <Input
                                id="contractDate"
                                name="contractDate"
                                placeholder={currentDate}
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
                                value={checkInsert? changeExDate(licenseAsset.contractDate, infoResult.priceUnit) : currentDate}
                                onChange={handleChangeLicenseAsset}
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
                            <Input  id="files" name="files" type="file" onChange={handleFileChange} multiple />
                            <FormText>
                                *첨부 가능 파일 형식 : pdf, zip, jpg, jpeg, png
                            </FormText>
                        </FormGroup>
                        <div className="text-center">
                            <Button className="btn" color="primary" onClick={handleClickAdd}>등록하기</Button>
                            <Button className="btn" color="secondary" onClick={moveToList}>리스트</Button></div>
                  
                </CardBody>
            </Card>
            </Form>
        </div>
    );
}
export default LicenseAssetRequestComponent;