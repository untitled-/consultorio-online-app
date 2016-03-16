'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('HeredoFamilyBkg', function ($resource, DateUtils) {
        return $resource('api/heredoFamilyBkgs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
