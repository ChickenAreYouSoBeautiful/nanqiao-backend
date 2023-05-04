package com.mi.nanqiao.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.mi.nanqiao.common.OrderState;
import com.mi.nanqiao.common.PayType;
import com.mi.nanqiao.entity.Orders;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "支付接口")
@RequestMapping("pay")
public class EasyPayController {

    private final OrdersService ordersService;

    public static final String ALIPAY_TRADE_PRECREATE_RESPONSE = "alipay_trade_precreate_response";
    public static final String CODE = "code";
    public static final String QR_CODE = "qr_code";
    /**
     * 支付
     *
     * @param code 订单编号
     * @return {@link String}
     */
    @GetMapping("aliPay/{code}")
    @ApiOperation("支付接口")
    @ApiImplicitParam(name = "code",value = "订单编号",required = true)
    public R pay(@PathVariable String code) {
        try {
            Assert.hasLength(code,"订单编号不能为空");
            Orders orders = ordersService.queryByNo(code);
            if (Objects.nonNull(orders)){
                if (OrderState.UNPAID.getCode() == orders.getFlag()){
                    String totalMoney = orders.getTotalMoney()+"";
                    // 2. 发起API调用（以创建当面付收款二维码为例）
                    String subject = orders.getProjectName();
                    log.info("发起支付,订单号{},订单的总金额{}", code, orders.getTotalMoney());
                    // 参数： subject: 商品信息 outTradeNo: 订单编号 totalAmount: 支付总金额
                    AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(subject, code, totalMoney);
                    // 3. 处理响应或异常
                    if (ResponseChecker.success(response)) {
                        log.info("支付API调用成功");
                        Map map = JSON.parseObject(response.getHttpBody(), Map.class);
                        if(Objects.nonNull(map)){
                            Map map2 = JSON.parseObject(map.get(ALIPAY_TRADE_PRECREATE_RESPONSE).toString(), Map.class);
                            if (Objects.nonNull(map2)) {
                                String code1 = map2.get(CODE).toString();
                                if (Objects.equals(code1, "10000")) {
                                    log.info("发起支付成功");
                                    return R.ok(map2.get(QR_CODE).toString());
                                }
                            }
                        }
                        return R.fail();
                    } else {
                        log.error("调用失败，原因：" + response.toString());
                    }
                }else {
                    return R.fail("订单已支付，无需重复支付");
                }
            }else {
                return R.fail("订单不存在");
            }

        } catch (Exception e) {
            log.error("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return R.fail();
    }


    /**
     * 查询支付状态
     *
     * @param code 订单编号
     * @return {@link String}
     */
    @GetMapping("queryPay/{code}")
    @ApiOperation("查询订单支付状态")
    @ApiImplicitParam(name = "code",value = "订单编号",required = true)
    public R<String> queryPay(@PathVariable String code) {
        Assert.hasText(code,"订单编号不能为空");
        try {
            // 通用能力中的一个接口 查询支付状态
            AlipayTradeQueryResponse response = Factory.Payment.Common().query(code);

            Map<String, Map<String, String>> map = JSON.parseObject(response.getHttpBody(), Map.class);
            String s = map.get("alipay_trade_query_response").get("trade_status");
            if (Objects.isNull(s)) {
                return R.fail("交易不存在");
            }
            if (s.equals("TRADE_SUCCESS")) {
                System.out.println("支付成功......");
                System.out.println("处理订单后续操作.....");
                // 修改订单状态为已支付
                if (ordersService.orderPay(code)){
                    return R.ok("支付成功");
                }
            }
            if (s.equals("WAIT_BUYER_PAY")) {
                return R.fail("待支付");
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return R.fail("查询失败");
    }


}
