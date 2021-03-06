'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('Immunization', function ($resource, DateUtils) {
        return $resource('api/immunizations/:id', {}, {
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
