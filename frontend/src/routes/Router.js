import React, { lazy } from "react";
import { Navigate } from "react-router-dom";

/****Layouts*****/
const FullLayout = lazy(() => import("../layouts/FullLayout.js"));

/***** Pages ****/

const Starter = lazy(() => import("../views/Starter.js"));
const MemberLogin = lazy(() => import("../pages/members/LoginPage.js"))
const MemberRegister = lazy(()=> import("../pages/members/RegisterPage.js"))
const MemberModify = lazy(()=> import("../pages/members/ModifyPage.js"))
const OrganizationPage = lazy(() => import("../pages/personnel/Organization/OrganizationPage.js"))
const ApplicationPage = lazy(() => import("../pages/personnel/Organization/AplicataionPage.js"))
const VacationApplicationPage = lazy(() => import("../pages/personnel/Vacation/VacationApplicationPage.js"))
const VacationAcceptionPage = lazy(() => import("../pages/personnel/Vacation/VacationAcceptionPage.js"))
/*****Routes******/
const ThemeRoutes = [
  
  { path:"/",
    element: <FullLayout/>,
    children: [
      { path:"/application", element: <ApplicationPage/>},
      { path:"/login", element: <MemberLogin/>},
      // { path:"/register", element: <MemberRegister/>},
      { path:"/modify", element: <MemberModify/>},
      { path: "/", element: <Navigate to="/starter" /> },
      { path: "/starter", exact: true, element: <Starter /> },
      { path: "/org", element: <OrganizationPage /> },
      { path: "/vac-app", element: <VacationApplicationPage /> },
      { path: "/vac-acc", element: <VacationAcceptionPage /> },
    ]   
  }
];

export default ThemeRoutes;
