'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('GynecoobstetricBkg', function ($resource, DateUtils) {
        return $resource('api/gynecoobstetricBkgs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.menarche = DateUtils.convertLocaleDateFromServer(data.menarche);
                    data.begginingSexualLife = DateUtils.convertLocaleDateFromServer(data.begginingSexualLife);
                    data.latestPapTest = DateUtils.convertLocaleDateFromServer(data.latestPapTest);
                    data.latestMammography = DateUtils.convertLocaleDateFromServer(data.latestMammography);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.menarche = DateUtils.convertLocaleDateToServer(data.menarche);
                    data.begginingSexualLife = DateUtils.convertLocaleDateToServer(data.begginingSexualLife);
                    data.latestPapTest = DateUtils.convertLocaleDateToServer(data.latestPapTest);
                    data.latestMammography = DateUtils.convertLocaleDateToServer(data.latestMammography);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.menarche = DateUtils.convertLocaleDateToServer(data.menarche);
                    data.begginingSexualLife = DateUtils.convertLocaleDateToServer(data.begginingSexualLife);
                    data.latestPapTest = DateUtils.convertLocaleDateToServer(data.latestPapTest);
                    data.latestMammography = DateUtils.convertLocaleDateToServer(data.latestMammography);
                    return angular.toJson(data);
                }
            }
        });
    });
