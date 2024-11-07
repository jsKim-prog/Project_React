import {  Button, DropdownItem, Nav, NavItem } from "reactstrap";
import {  Link, useLocation } from "react-router-dom";
import user1 from "../assets/images/users/user4.jpg";
import probg from "../assets/images/bg/download.jpg";
import PageInfo from "./PageInfo";


/*메뉴 내용->PagiInfo */




const Sidebar = () => {
  
  
  const showMobilemenu = () => {
    document.getElementById("sidebarArea").classList.toggle("showSidebar");
  };
  let location = useLocation();

  /*custom method*/
  const {menu1, menu2, menu3, menu4} = PageInfo();
  console.log(menu1);
  console.log(menu2);
  console.log(menu3);
  console.log(menu4);

  return (
    <div>
      <div className="d-flex align-items-center"></div>
      <div
        className="profilebg"
        style={{ background: `url(${probg}) no-repeat` }}
      >
        <div className="p-3 d-flex">
          <img src={user1} alt="user" width="50" className="rounded-circle" />
          <Button
            color="white"
            className="ms-auto text-white d-lg-none"
            onClick={() => showMobilemenu()}
          >
            <i className="bi bi-x"></i>
          </Button>
        </div>
        <div className="bg-dark text-white p-2 opacity-75">Steave Rojer</div>
      </div>
      <div className="p-3 mt-2">
        <Nav vertical className="sidebarNav">
        <NavItem className="sidenav-bg">
          <Link
            to={menu1.topMenu.href}
            className={
              location.pathname === menu1.topMenu.href
                ? "active nav-link py-3"
                : "nav-link text-secondary py-3"}>
            <i className={menu1.topMenu.icon}></i>
            <span className="ms-3 d-inline-block">{menu1.topMenu.title}</span>
          </Link>
        </NavItem>
        <NavItem className="sidenav-bg">
          <Link
            to={menu2.topMenu.href}
            className={
              location.pathname === menu2.topMenu.href
                ? "active nav-link py-3"
                : "nav-link text-secondary py-3"}>
            <i className={menu2.topMenu.icon}></i>
            <span className="ms-3 d-inline-block">{menu2.topMenu.title}</span>
          </Link>
        </NavItem>
        {menu2.subMenus.map(sub=>(
          <ul className="offset-1">
            <Link to={sub.href}><li>{sub.title}</li></Link>
          </ul>
        ))}
        <NavItem className="sidenav-bg">
          <Link
            to={menu3.topMenu.href}
            className={
              location.pathname === menu3.topMenu.href
                ? "active nav-link py-3"
                : "nav-link text-secondary py-3"}>
            <i className={menu3.topMenu.icon}></i>
            <span className="ms-3 d-inline-block">{menu3.topMenu.title}</span>
          </Link>
        </NavItem>
        {menu3.subMenus.map(sub=>(
          <ul className="offset-1">
            <Link to={sub.href}><li>{sub.title}</li></Link>
          </ul>
        ))}
        <NavItem className="sidenav-bg">
          <Link
            to={menu4.topMenu.href}
            className={
              location.pathname === menu4.topMenu.href
                ? "active nav-link py-3"
                : "nav-link text-secondary py-3"}>
            <i className={menu4.topMenu.icon}></i>
            <span className="ms-3 d-inline-block">{menu4.topMenu.title}</span>
          </Link>
        </NavItem>
        {menu4.subMenus.map(sub=>(
          <ul className="offset-1">
            <Link to={sub.href}><li>{sub.title}</li></Link>
          </ul>
        ))}




          <Button
            color="danger"
            tag="a"
            target="_blank"
            className="mt-3"
            href="https://wrappixel.com/templates/materialpro-react-admin/?ref=33"
          >
            결제
          </Button>
        </Nav>
      </div>
    </div>
  );
};

export default Sidebar;