'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('Drug', function ($resource, DateUtils) {
        return $resource('api/drugs/:id', {}, {
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
