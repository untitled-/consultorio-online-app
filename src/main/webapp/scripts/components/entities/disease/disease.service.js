'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Disease', function ($resource, DateUtils) {
        return $resource('api/diseases/:id', {}, {
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
