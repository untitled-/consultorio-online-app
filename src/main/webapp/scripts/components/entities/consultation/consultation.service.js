'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('Consultation', function ($resource, DateUtils) {
        return $resource('api/consultations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.consultationDate = DateUtils.convertLocaleDateFromServer(data.consultationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.consultationDate = DateUtils.convertLocaleDateToServer(data.consultationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.consultationDate = DateUtils.convertLocaleDateToServer(data.consultationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
