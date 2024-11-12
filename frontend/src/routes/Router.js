import { lazy } from "react";
import { Navigate } from "react-router-dom";

/****Layouts*****/
const FullLayout = lazy(() => import("../layouts/FullLayout.js"));

/***** Pages ****/

const Starter = lazy(() => import("../views/Starter.js"));

const Project = lazy(() => import("../pages/project/ProjectList"));
const ProjectRegister = lazy(() => import("../pages/project/ProjectRegister"))
const ProjectRead = lazy(() => import("../pages/project/ProjectRead"));
const ProjectModify = lazy(() => import("../pages/project/ProjectModify"));

const Licenselist = lazy(()=>import("../pages/distribution/LicenseAssetList"))
const LicenseInfoRegister = lazy(()=>import("../pages/distribution/LicenseInfoAdd"))
const LicenseAssetAdd = lazy(()=>import("../pages/distribution/LicenseAssetAdd"))

/*****Routes******/

const ThemeRoutes = [
  {
    path: "/",
    element: <FullLayout />,
    children: [
      {path: "/project", exact: true, element: <Project/>},
      {path: "/project/add", exact: true, element: <ProjectRegister/>},
      {path: "/project/:id", exact: true, element: <ProjectRead/>},
      {path: "/project/modify/:id", exact: true, element: <ProjectModify/>},
      { path: "/", element: <Navigate to="/starter" /> },
      { path: "/starter", exact: true, element: <Starter /> },     
      { path: "/dist/licenses", exact: true, element:<Licenselist/>},
      { path: "/dist/licenses/request", exact: true, element:<LicenseAssetAdd/>},
      { path: "/dist/licenses/register", exact: true, element:<LicenseInfoRegister/>},
    ],
  },
];

export default ThemeRoutes;
