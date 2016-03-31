'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


