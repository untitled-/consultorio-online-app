'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('Symptom', function ($resource, DateUtils) {
        return $resource('api/symptoms/:id', {}, {
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
