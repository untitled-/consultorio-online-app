'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('LabTestSearch', function ($resource) {
        return $resource('api/_search/labTests/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
