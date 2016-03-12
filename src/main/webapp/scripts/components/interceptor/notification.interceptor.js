 'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-consultorioOnlineUiApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-consultorioOnlineUiApp-params')});
                }
                return response;
            }
        };
    });
