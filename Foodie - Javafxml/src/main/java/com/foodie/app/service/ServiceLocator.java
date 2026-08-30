package com.foodie.app.service;

public class ServiceLocator {
    public static boolean USE_REAL_API = true; // Set to true as requested by user to connect API

    private static CustomerService customerService;
    private static DashboardDataProvider dashboardService;
    private static AuthService authService;
    private static MenuService menuService;
    private static OrderService orderService;

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = USE_REAL_API ? new HttpCustomerService() : new MockCustomerService();
        }
        return customerService;
    }

    public static DashboardDataProvider getDashboardService() {
        if (dashboardService == null) {
            dashboardService = USE_REAL_API ? new HttpDashboardService() : new MockDashboardDataProvider();
        }
        return dashboardService;
    }

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = USE_REAL_API ? new HttpAuthService() : new MockAuthService();
        }
        return authService;
    }

    public static MenuService getMenuService() {
        if (menuService == null) {
            // Only HttpMenuService created for now, as we're focusing on API integration
            menuService = new HttpMenuService(); 
        }
        return menuService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            orderService = new HttpOrderService();
        }
        return orderService;
    }
}

