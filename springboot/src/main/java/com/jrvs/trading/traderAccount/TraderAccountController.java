package com.jrvs.trading.traderAccount;

import com.jrvs.trading.ResponseExceptionUtil;
import com.jrvs.trading.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

    private TraderAccountService traderAccountService;

    @Autowired
    public TraderAccountController(TraderAccountService traderAccountService) {
        this.traderAccountService = traderAccountService;
    }

    @PostMapping(
            path = "firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TraderAccountView createTrader(
            @PathVariable String firstname,
            @PathVariable String lastname,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dob,
            @PathVariable String country,
            @PathVariable String email
            ) {
        try {
            Trader trader = new Trader();
            trader.firstName = firstname;
            trader.lastName = lastname;
            trader.dob = Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant());
            trader.country = country;
            trader.email = email;
            return traderAccountService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TraderAccountView createTrader(@RequestBody Trader trader) {
        try {
            return traderAccountService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @DeleteMapping(path = "/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId) {
        try {
            traderAccountService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/deposit/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account depositFunds(@PathVariable Integer traderId, @PathVariable double amount) {
        try {
            return traderAccountService.deposit(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/withdraw/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdrawFunds(@PathVariable Integer traderId, @PathVariable double amount) {
        try {
            return traderAccountService.withdraw(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
