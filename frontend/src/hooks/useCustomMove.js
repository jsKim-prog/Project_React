// 페이지 넘어가는 기능을 한곳에 모아두기 위함.

import { useState } from "react"
import { createSearchParams, useNavigate, useSearchParams } from "react-router-dom"

// getNum을 호출할때 파라미터, 기본값을 받아옴
const getNum = (param, defaultValue) => {
    if (!param) { // 파라미터가 없으면
        return defaultValue // 기본값인 1, 10
    }
    return parseInt(param) // 파라미터가 있으면 인트로 변환후 리턴
}

const useCustomMove = () => {
    const navigate = useNavigate();
    // 현재 페이지를 다시 클릭하면 서버 호출을 하지않음,
    // 동일한 page, size더라도 매번 서버 호출하고 싶으면 매번 변하는 상태값을 이용해야함.
    // 즉 버튼 클릭시 true와 false 값이 번갈아 가면서 변경되거나, 숫자가 계속올라가거나, 현재시간등을 이용할수있음
    const [refresh, setRefresh] = useState(false)
    const [queryParams] = useSearchParams()
    const page = getNum(queryParams.get("page"), 1)
    const size = getNum(queryParams.get("size"), 10)
    // 디폴트 쿼리 page=1&size=10
    const queryDefault = createSearchParams({ page, size }).toString()

    // 목록으로 돌아가는 함수
    const moveToList = (pageParam) => {
        let queryStr = ""

        if (pageParam) { // pageParam이 있으면,
            // 파라미터 추출
            const pageNum = getNum(pageParam.page, 1)
            const sizeNum = getNum(pageParam.size, 10)
            // 하나로 합침
            queryStr = createSearchParams({
                page: pageNum,
                size: sizeNum
            }).toString()
        } else { // pageParam없으면 기본값 1, 10
            queryStr = queryDefault
        }
        setRefresh(!refresh)
        navigate({
            pathname: "../project",
            search: queryStr,
        })
    }


    // 수정 화면으로 넘어가는 메서드
    const moveToModify = (id) => {
        console.log(queryDefault);

        navigate({
            pathname: `../project/modify/${id}`,
            search: queryDefault // 수정시 기존의 쿼리 스트링 유지를 위해
        })
    }

    // 조회 화면으로 넘어가는 메서드
    const moveToRead = (id) => {
        console.log(queryDefault)

        navigate({
            pathname: `../project/${id}`,
            search: queryDefault
        })
    }

    return (
        { moveToList, moveToModify, moveToRead, page, size }
    );
}

export default useCustomMove;