'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Contraceptives', function ($resource, DateUtils) {
        return $resource('api/contraceptivess/:id', {}, {
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
