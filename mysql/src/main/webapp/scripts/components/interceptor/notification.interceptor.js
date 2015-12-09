 'use strict';

angular.module('ontimeApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-ontimeApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-ontimeApp-params')});
                }
                return response;
            }
        };
    });
