package com.mi.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.entity.Shop;
import com.mi.nanqiao.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/insert")
    public R<Shop> insert(@RequestBody Shop shop){
        return R.ok(shopService.insert(shop));
    }


    @PostMapping("/update")
    public R<Shop> updateById(@RequestBody Shop shop){
        return R.ok( shopService.update(shop));
    }

    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Integer id){
        return R.ok(shopService.deleteById(id));
    }

    @GetMapping("/{id}")
    public R queryById(@PathVariable Integer id){
        return R.ok(shopService.queryById(id));
    }

    @PostMapping("page")
    public R queryByPage(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                         @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){
        PageInfo<Shop> shopPageInfo = shopService.queryByPage(page, size);
        return R.ok(shopPageInfo);
    }

    @GetMapping("/queryShopByType/{id}")
    Shop queryByLbid(@PathVariable Integer id){
        return shopService.queryByLbid(id);
    }
}
