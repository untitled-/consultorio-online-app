'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Alergy', function ($resource, DateUtils) {
        return $resource('api/alergys/:id', {}, {
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
