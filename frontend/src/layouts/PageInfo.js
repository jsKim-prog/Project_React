

//menu data
//menu1
const menu1_topMenu = {
    title: "대시보드",
    href: "/starter",
    icon: "bi bi-speedometer2",
    role: ""
  }
  //menu2
  const menu2_topMenu = {
    title: "프로젝트 관리",
    href: "/project",
    icon: "bi bi-card-text",
    role: ""
  }
  
  const menu2_sub1 = {
    title: "프로젝트 관리",
    href: "/project",
    role: ""
  }
  
  const menu2_sub2 = {
    title: "이슈 관리",
    href: "/",
    role: ""
  }
  
  //menu3
  const menu3_topMenu = {
    title: "인사관리",
    href: "/org",
    icon: "bi bi-people",
    role: ""
  }
  
  const menu3_sub1 = {
    title: "사원관리",
    href: "/org",
    role: ""
  }
  
  const menu3_sub2 = {
    title: "지원서관리",
    href: "/application",
    role: ""
  }
  
  //menu4
  const menu4_topMenu = {
    title: "자원관리",
    href: "/",
    icon: "bi bi-hdd-stack",
    role: ""
  }
  
  const menu4_sub1 = {
    title: "자원사용신청(문서함)",
    href: "/",
    role: ""
  }
  
  const menu4_sub2 = {
    title: "하드웨어 관리",
    href: "/",
    role: ""
  }
  const menu4_sub3 = {
    title: "라이선스 관리",
    href: "/dist/licenses",
    role: ""
  }
  
  const menu4_sub4 = {
    title: "고객사 관리",
    href: "/",
    role: ""
  }
  const menu4_sub5 = {
    title: "사무용품 관리",
    href: "/",
    role: ""
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