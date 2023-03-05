package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.query.profession.GetProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.SaveProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.UpdateProfessionQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionDetailVO;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionPartVO;
import top.liubaiblog.masterstudio.service.ProfessionService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@RequestMapping("/profession")
@Api(tags = "岗位管理控制器")
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    @GetMapping("/names")
    @ApiOperation("获取岗位名列表")
    public ResponseData<List<ProfessionPartVO>> names() {
        List<ProfessionPartVO> names = professionService.names();
        return ResponseDataBuilder.builder().success(names);
    }

    @GetMapping("/name/{name}")
    @ApiOperation("检查岗位名是否重复")
    public ResponseData<String> professionNameExist(@PathVariable("name")String name) {
        String existPid = professionService.exist(name);
        return ResponseDataBuilder.builder().success(existPid);
    }

    @PostMapping("/list")
    @ApiOperation("获取岗位列表")
    public ResponseData<PageVO<ProfessionPartVO>> list(@RequestBody @Validated GetProfessionQuery professionQuery) {
        PageVO<ProfessionPartVO> pages = professionService.listByProfessionQuery(professionQuery);
        return ResponseDataBuilder.builder().success(pages);
    }

    @GetMapping("/{pid}")
    @ApiOperation("获取岗位详细信息")
    public ResponseData<ProfessionDetailVO> getById(@PathVariable("pid") Long pid) {
        ProfessionDetailVO professionDetailVO = professionService.getById(pid);
        return ResponseDataBuilder.builder().success(professionDetailVO);
    }

    @PostMapping
    @ApiOperation("保存岗位")
    @SetLog(module = LogModuleEnums.PROFESSION, operType = "保存岗位")
    public ResponseData<Void> save(@RequestBody @Validated SaveProfessionQuery saveProfessionQuery) {
        if (professionService.saveByProfessionQuery(saveProfessionQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @PutMapping
    @ApiOperation("修改岗位")
    @SetLog(module = LogModuleEnums.PROFESSION, operType = "修改岗位")
    public ResponseData<Void> update(@RequestBody @Validated UpdateProfessionQuery updateProfessionQuery) {
        if (professionService.updateByProfessionQuery(updateProfessionQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @DeleteMapping("/{pid}")
    @ApiOperation("删除岗位")
    @SetLog(module = LogModuleEnums.PROFESSION, operType = "修改岗位")
    public ResponseData<Void> delete(@PathVariable("pid") Long pid) {
        if (professionService.removeById(pid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }


}
