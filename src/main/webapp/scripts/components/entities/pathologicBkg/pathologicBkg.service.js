'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('PathologicBkg', function ($resource, DateUtils) {
        return $resource('api/pathologicBkgs/:id', {}, {
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
