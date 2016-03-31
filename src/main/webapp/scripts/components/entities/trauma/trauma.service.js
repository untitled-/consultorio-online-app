'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Trauma', function ($resource, DateUtils) {
        return $resource('api/traumas/:id', {}, {
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
