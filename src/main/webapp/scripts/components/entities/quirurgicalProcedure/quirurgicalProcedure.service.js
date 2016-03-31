'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('QuirurgicalProcedure', function ($resource, DateUtils) {
        return $resource('api/quirurgicalProcedures/:id', {}, {
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
