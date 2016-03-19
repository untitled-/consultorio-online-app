'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('DrugSearch', function ($resource) {
        return $resource('api/_search/drugs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
