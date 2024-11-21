

//menu data
//menu1
const menu1_topMenu = {
  title: "대시보드",
  href: "/starter",
  icon: "bi bi-speedometer2",
  role: "",
  id: "1_0"
}
//menu2
const menu2_topMenu = {
  title: "프로젝트 관리",
  href: "/project",
  icon: "bi bi-card-text",
  role: "",
  id: "2_0"
}

const menu2_sub1 = {
  title: "프로젝트 관리",
  href: "/project",
  role: "",
  id: "2_1"
}

const menu2_sub2 = {
  title: "이슈 관리",
  href: "/",
  role: "",
  id: "2_2"
}

//menu3
const menu3_topMenu = {
  title: "인사관리",
  href: "/",
  icon: "bi bi-people",
  role: "",
  id: "3_0"
}

const menu3_sub1 = {
  title: "사원관리",
  href: "/",
  role: "",
  id: "3_1"
}

const menu3_sub2 = {
  title: "근태관리",
  href: "/",
  role: "",
  id: "3_2"
}

//menu4
const menu4_topMenu = {
  title: "자원관리",
  href: "/",
  icon: "bi bi-hdd-stack",
  role: "",
  id: "4_0"
}

const menu4_sub1 = {
  title: "자원사용신청(문서함)",
  href: "/",
  role: "",
  id: "4_1"
}

const menu4_sub2 = {
  title: "하드웨어 관리",
  href: "/",
  role: "",
  id: "4_2"
}
const menu4_sub3 = {
  title: "라이선스 관리",
  href: "/dist/licenses",
  role: "",
  id: "4_3"
}

const menu4_sub4 = {
  title: "고객사 관리",
  href: "/",
  role: "",
  id: "4_4"
}
const menu4_sub5 = {
  title: "사무용품 관리",
  href: "/",
  role: "",
  id: "4_5"
}


export const PageInfo = () => {


  const menu1 = {
    topMenu: menu1_topMenu,
    subMenus: ""
  }

  const menu2 = {
    topMenu: menu2_topMenu,
    subMenus: [menu2_sub1, menu2_sub2]
  }

  const menu3 = {
    topMenu: menu3_topMenu,
    subMenus: [menu3_sub1, menu3_sub2]
  }

  const menu4 = {
    topMenu: menu4_topMenu,
    subMenus: [menu4_sub1, menu4_sub2, menu4_sub3, menu4_sub4, menu4_sub5]
  }


  return { menu1, menu2, menu3, menu4 };

}

export default PageInfo;