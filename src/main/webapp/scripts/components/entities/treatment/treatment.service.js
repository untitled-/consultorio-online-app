'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('Treatment', function ($resource, DateUtils) {
        return $resource('api/treatments/:id', {}, {
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
