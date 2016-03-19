'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('LabTest', function ($resource, DateUtils) {
        return $resource('api/labTests/:id', {}, {
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
