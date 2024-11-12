import axios from "axios"

export const API_DISTRIBUSUION_HOST = "http://192.168.0.211:80"
const prefix = `${API_DISTRIBUSUION_HOST}/dist/license`
const lno = 0
const ano = 0

/* 등록 : info license */
export const registInfo = async (dto) => {
    const header = { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
    const res = await axios.post(`${prefix}/info`, dto)
    return res.data
}
/* 조회 one : info license */
export const getOneInfo = async (lno) => {
    const res = await axios.get(`${prefix}/info/${lno}`)
    return res.data
}
/* 조회 all : info license */
export const getListInfo = async (pageParam) => {
    const { page, size } = pageParam
    const res = await axios.get(`${prefix}/info`)
    return res.data
}
/* 변경 :info license */
export const modInfo = async (dto) => {
    const res = await axios.put(`${prefix}/info/${lno}`, dto)
    return res.data
}
/* 삭제처리(상태변경) : info license */
export const delInfo = async (lno) => {
    const res = await axios.delete(`${prefix}/info/${lno}`)
    return res.data
}
/* 영구삭제 : info license */
export const delForeverInfo = async (lno) => {
    const res = await axios.delete(`${prefix}/info_del/${lno}`)
    return res.data
}


/* 등록 : asset license */
export const registAsset = async (dto) => {
    const header = { headers: { "Content-Type": "multipart/form-data" } }
    const res = await axios.post(`${prefix}/asset`, dto, header)
    return res.data
}
/* 조회 one :asset license(with file list) */
export const getOneAsset = async (ano) => {
    const res = await axios.get(`${prefix}/asset/${ano}`)
    return res.data
}
/* 조회 all : asset license(with paging+file count) */
export const getListAsset = async (pageParam) => {
    const { page, size } = pageParam
    const res = await axios.get(`${prefix}/asset`)
    return res.data
}
/* 변경 :asset license(with file list) */
export const modAsset = async (dto) => {
    const header = { headers: { "Content-Type": "multipart/form-data" } }
    const res = await axios.put(`${prefix}/asset/${ano}`, dto, header)
    return res.data
}
/* 삭제처리(상태변경) : asset license(with file list) */
export const delAsset = async (ano) => {
    const res = await axios.delete(`${prefix}/asset/${ano}`)
    return res.data
}
/* 영구삭제 : asset license(with file list) */
export const delForeverAsset = async (ano) => {
    const res = await axios.delete(`${prefix}/asset_del/${ano}`)
    return res.data
}


