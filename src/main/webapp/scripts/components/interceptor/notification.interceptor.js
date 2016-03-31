 'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-consultorioOnlineAppApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-consultorioOnlineAppApp-params')});
                }
                return response;
            }
        };
    });
