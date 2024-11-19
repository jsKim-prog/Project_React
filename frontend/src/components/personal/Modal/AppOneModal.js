import { useEffect, useState } from "react";
import { Card, Row, Col, CardBody, Label, Button, Modal, ModalHeader, ModalBody } from "reactstrap";
import FetchingModal from "../../common/FetchingModal"; 
import { readOne } from "../../../api/applicationAPI";

const AppOneModal = ({ no, isOpen, closeModal }) => {
  const initState = {
    no: '',
    uploadFileNames: [],
  };

  const [memberS, setMember] = useState(initState);
  const [fetching, setFetching] = useState(false);
  const [imageUrl, setImageUrl] = useState(null); // 이미지 URL 상태 추가

  // 파일 다운로드 처리 함수
  const fetchApplication = async (mno) => {
    console.log("fetchApplication 실행")
    try {
      // JSON 데이터 요청
      const response = await fetch(`/api/getOne/${mno}`);
      const result = await response.json();  // JSON 응답 받기
      const fileName = result.application.uploadFileNames[0];
  
      // 파일 다운로드를 별도로 처리
      const fileResponse = await fetch(`/api/getFile/${fileName}`);
      const fileBlob = await fileResponse.blob();  // 파일 응답을 Blob 형태로 받기
      const fileURL = URL.createObjectURL(fileBlob);  // Blob을 URL로 변환
      console.log(fileURL)

      // 파일 다운로드 트리거
      const link = document.createElement('a');
      link.href = fileURL;
      link.download = fileName;  // 다운로드할 파일 이름 설정
      link.click();  // 다운로드 트리거
    } catch (error) {
      console.error("Error fetching application:", error);
    }
  };

  useEffect(() => {
    if (!no) return; // `no`가 없으면 API 호출하지 않음

    setFetching(true);

    // 데이터 가져오기
    readOne(no)
      .then(data => {
        setMember(data);

        // 이미지 URL 설정
        if (data.uploadFileNames && data.uploadFileNames.length > 0) {
          const imageName = data.uploadFileNames[0]; // 첫 번째 파일명을 사용
          console.log(imageName)
          fetch(`/api/getFile/${imageName}`)
            .then(response => response.blob())
            .then(blob => {
              const url = URL.createObjectURL(blob); // Blob을 URL로 변환
              console.log(url)
              setImageUrl(url); // 이미지 URL 상태 업데이트
            })
            .catch(error => console.error("Error fetching image:", error));
        }

        setFetching(false);
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
        setFetching(false);
      });
  }, [no]);

  if (!no) return null;

  return (
    <Modal isOpen={isOpen} toggle={closeModal} backdrop="static" size="sm">
      {fetching ? (
        <FetchingModal />
      ) : (
        <>
          <ModalHeader toggle={closeModal}> 지원자 이력서 </ModalHeader>
          <ModalBody>
            <Row>
              <Col>
                {/* Card-1 */}
                <Card>
                  <CardBody>
                    <table>
                      <tbody>
                        <tr>
                          <td>
                            <Label for="no">입사지원번호</Label>
                          </td>
                          <td>{memberS.no}</td>
                        </tr>
                        <tr>
                          <td>
                            <Label>지원서</Label>
                          </td>
                          <td>
                            {imageUrl ? (
                              <img src={imageUrl} alt="지원서 이미지" width="200" />
                            ) : (
                              <p>이미지가 없습니다.</p>
                            )}
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <Button color="primary" onClick={() => fetchApplication(memberS.no)}>
                              파일 다운로드
                            </Button>
                          </td>
                          <td>
                            <Button color="secondary" onClick={closeModal}>닫기</Button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </ModalBody>
        </>
      )}
    </Modal>
  );
};

export default AppOneModal;
