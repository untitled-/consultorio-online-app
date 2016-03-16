'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('DiseaseSearch', function ($resource) {
        return $resource('api/_search/diseases/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
