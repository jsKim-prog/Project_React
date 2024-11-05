import axios from "axios";

const API_PRODUCT_HOST = `http://ysy.tplinkdns.com/api/project`
const list = `${API_PRODUCT_HOST}/list`


// 리스트
export const getList = async (pageParam) => {
    const {page,size} = pageParam

    const res = await axios.get(list, {
        params: {
            page: page,
            size: size
        }
    })
    return res.data
}

// 등록
export const postAdd = async (project) => {
    const header = {
        headers: {
            "Content-Type": "application/json"
        }
    }

    const res = await axios.post(`${API_PRODUCT_HOST}/`, project, header)
    return res.data;
}

// 조회
export const getOne = async (id) => {
    const res = await axios.get(`${API_PRODUCT_HOST}/${id}`);
    return res.data
}

// 수정
export const putOne = async (project, id) => {
    const header = {
        headers: {
            "Content-Type": "application/json"
        }
    }
    const res = await axios.put(`${API_PRODUCT_HOST}/${id}`, project, header)
    return res.data;
}

// 삭제
export const deleteOne = async (id) => {
    const res = await axios.delete(`${API_PRODUCT_HOST}/${id}`);
    return res.data
}