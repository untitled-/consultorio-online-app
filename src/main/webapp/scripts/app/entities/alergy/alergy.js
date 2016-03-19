'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('alergy', {
                parent: 'entity',
                url: '/alergys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.alergy.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/alergy/alergys.html',
                        controller: 'AlergyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('alergy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('alergy.detail', {
                parent: 'entity',
                url: '/alergy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.alergy.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/alergy/alergy-detail.html',
                        controller: 'AlergyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('alergy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Alergy', function($stateParams, Alergy) {
                        return Alergy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('alergy.new', {
                parent: 'alergy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/alergy/alergy-dialog.html',
                        controller: 'AlergyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('alergy', null, { reload: true });
                    }, function() {
                        $state.go('alergy');
                    })
                }]
            })
            .state('alergy.edit', {
                parent: 'alergy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/alergy/alergy-dialog.html',
                        controller: 'AlergyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Alergy', function(Alergy) {
                                return Alergy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('alergy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('alergy.delete', {
                parent: 'alergy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/alergy/alergy-delete-dialog.html',
                        controller: 'AlergyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Alergy', function(Alergy) {
                                return Alergy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('alergy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
