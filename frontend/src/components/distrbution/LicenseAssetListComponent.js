import { useEffect, useState } from "react"
import useCustomMove from "../../hooks/useCustomMove"
import { getListAsset } from "../../api/LicenseApi"
import { Card, CardBody, CardSubtitle, CardTitle, Table } from "reactstrap"

const initState={ //PageResponseDTO
    dtoList:[],
    pageNumList:[],
    pageRequestDTO:null,
    prev:false, 
    next:false,
    totalCount: 0, 
    prevPage:0, 
    nextPage:0, 
    totalPage:0, 
    current:0
}


const LicenseAssetListComponent = () => {
    const {page, size, refresh, moveToList, moveToRead} = useCustomMove()
    const [serverData, setServerData] = useState(initState)
    const [fetching, setFetching] = useState(false) //진행모달
    //const {exceptionHandle} = useCustomLogin()
    useEffect(()=>{
        setFetching(true)
        getListAsset({page, size}).then(data =>{
            console.log(data)
            setServerData(data)
            setFetching(false)
        })
        //.catch(err=> exceptionHandle(err))
    },[page, size, refresh])
    return(
        <div>
        <Card>
          <CardBody>
            <CardTitle tag="h5">License List</CardTitle>
            <CardSubtitle className="mb-2 text-muted" tag="h6">
              라이선스 보유 현황
            </CardSubtitle>
  
            <Table className="no-wrap mt-3 align-middle" responsive borderless>
              <thead>
                <tr>
                  <th>구분</th>
                  <th>상품명</th>  
                  <th>사용처</th>
                  <th>사용중 개수/최대사용가능 개수</th>
                  <th>만료일</th>
                </tr>
              </thead>
              <tbody>
                {serverData.dtoList.map((assetDto, index) => (
                  <tr key={assetDto.ano} className="border-top">
                    <td>{assetDto.type}</td>
                    <td>{assetDto.rightName} {assetDto.fileCount > 0? <span className="badge bg-primary rounded-pill">{assetDto.fileCount}</span>:""}</td>
                    <td>{assetDto.purpose}</td>
                    <td>{assetDto.currentUseCount} / {assetDto.totalUseCount}</td>
                    <td>{assetDto.expireDate}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </CardBody>
        </Card>
      </div>
    );
}
export default LicenseAssetListComponent;
