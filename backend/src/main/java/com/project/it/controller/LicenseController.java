package com.project.it.controller;

import com.project.it.dto.*;
import com.project.it.service.AssetLicenseService;
import com.project.it.service.InfoLicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/dist/license")
public class LicenseController {
    private final AssetLicenseService assetService;
    private final InfoLicenseService infoService;

    /* info license ----------------------------------------------------------------*/
    //등록 : info license
    @PostMapping("/info")
    public Map<String, Long> registerInfo(InfoLicenseDTO infoLicenseDTO){
        log.info("(Controller)license info register 실행====등록할 파일 : " + infoLicenseDTO);
        Long lno = infoService.register(infoLicenseDTO);
        return Map.of("result", lno);
    }


    //조회 one : info license
    @GetMapping("/info/{lno}")
    public InfoLicenseDTO readInfo(@PathVariable(name = "lno") Long lno){
        return infoService.getOne(lno);
    }

    //조회 all : info license
    @GetMapping("/info")
    public List<InfoLicenseDTO> listInfo(){
        return infoService.getList();
    }

    //변경 :info license
    @PutMapping("/info/{lno}")
    public Map<String, String> modifyInfo(@PathVariable("lno") Long lno, InfoLicenseDTO infoLicenseDTO){
        infoLicenseDTO.setLno(lno);
        infoService.update(infoLicenseDTO);
        return Map.of("RESULT", "SUCCESS");
    }
    //삭제처리(상태변경) : info license
    @DeleteMapping("/info/{lno}")
    public Map<String, String> remove(@PathVariable("lno")Long lno){
        infoService.remove(lno);
        return Map.of("RESULT", "SUCCESS");
    }
    //영구삭제 : info license
    @DeleteMapping("/info_del/{lno}")
    public Map<String, String> removeForeverInfo(@PathVariable("lno")Long lno){
        infoService.removeForever(lno);
        return Map.of("RESULT", "SUCCESS");
    }

  /* asset license ----------------------------------------------------------------*/
    //등록 : asset license
    @PostMapping("/asset")
    public Map<String, Long> registerAsset(AssetLicenseDTO assetLicenseDTO){
        log.info("(Controller)license asset register 실행====등록할 파일 : " + assetLicenseDTO);
        Long ano = assetService.register(assetLicenseDTO);
        return Map.of("result", ano);
    }
    //조회 one : asset license(with file list)
    @GetMapping("/asset/{ano}")
    public AssetLicenseDTO readAsset(@PathVariable(name = "ano") Long ano){
        return assetService.getOne(ano);
    }
    //조회 all : asset license(with paging+file count)
    @GetMapping("/asset")
    public PageResponseDTO<AssetLicenseListDTO> getListAsset(PageRequestDTO pageRequestDTO){
        return assetService.getList(pageRequestDTO);
    }

    //변경 :asset license(with file list)
    @PutMapping("/asset/{ano}")
    public Map<String, String> modifyAsset(@PathVariable("ano")Long ano, AssetLicenseDTO assetLicenseDTO){
        assetLicenseDTO.setAno(ano);
        assetService.update(assetLicenseDTO);
        return Map.of("RESULT", "SUCCESS");
    }
    //삭제처리(상태변경) : asset license(with file list)
    @DeleteMapping("/asset/{ano}")
    public Map<String, String> removeAsset(@PathVariable("ano")Long ano){
        assetService.remove(ano);
        return Map.of("RESULT", "SUCCESS");
    }
    //영구삭제 : asset license(with file list)
    @DeleteMapping("/asset_del/{ano}")
    public Map<String, String> removeForeverAsset(@PathVariable("ano")Long ano){
        assetService.removeForever(ano);
        return Map.of("RESULT", "SUCCESS");
    }

}
