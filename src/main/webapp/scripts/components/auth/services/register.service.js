'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


