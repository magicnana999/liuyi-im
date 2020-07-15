package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.BaseController;
import com.creolophus.liuyi.storage.IncrementIdStorage;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/liuyi/id/increment_id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IncrementIdController extends BaseController {

    @Resource
    private IncrementIdStorage incrementStorage;

    @RequestMapping(value = "/next_id", method = RequestMethod.POST)
    public ApiResult nextId(@RequestParam("userId") Long userId) {
        return new ApiResult(incrementStorage.increase(userId));
    }

}
